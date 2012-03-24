package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.gui.playeraction.*;
import stegen.client.service.*;
import stegen.shared.*;

public class PlayerMiscCommandsPresenter implements Presenter {
	private final Display view;
	final AbstractAsyncCallback<List<PlayerCommandDto>> miscPlayerCallback = createMiscPlayerCallback();
	final RefreshEventHandler refreshEventHandler = refreshEventHandler();
	final UndoEventHandler undoEventHandler = createUndoEventHandler();
	final ChallengeEventHandler challangeEventHandler = createChallengeEventHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler =  createChangeNicknameEventHandler();
	private final com.google.gwt.event.shared.EventBus gwtEventBus;
	private final PlayerCommandServiceAsync playerCommandService;

	public interface Display {
		void changePlayerMiscCommandList(List<PlayerMiscCommandRow> content);
	}

	public PlayerMiscCommandsPresenter(Display scoreView,com.google.gwt.event.shared.EventBus gwtEventBus,PlayerCommandServiceAsync playerCommandService) {
		this.view = scoreView;
		this.gwtEventBus = gwtEventBus;
		this.playerCommandService = playerCommandService;
	}

	@Override
	public void go() {
		initEvents();
		loadPlayerMiscCommands();
	}

	private void initEvents() {
		gwtEventBus.addHandler(RefreshEvent.TYPE,refreshEventHandler);
		gwtEventBus.addHandler(UndoEvent.TYPE, undoEventHandler);
		gwtEventBus.addHandler(ChallengeEvent.TYPE, challangeEventHandler);
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
	}

	private void loadPlayerMiscCommands() {
		playerCommandService.getMiscPlayerCommandStack(10, miscPlayerCallback);
	}

	private AbstractAsyncCallback<List<PlayerCommandDto>> createMiscPlayerCallback(){
		return new AbstractAsyncCallback<List<PlayerCommandDto>>() {
			
			@Override
			public void onSuccess(List<PlayerCommandDto> gameResults) {
				List<PlayerMiscCommandRow> content = new ArrayList<PlayerMiscCommandRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new PlayerMiscCommandRow(playerCommandDto.player.nickname,
							playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changePlayerMiscCommandList(content);
			}
		};
	}

	private RefreshEventHandler refreshEventHandler(){
		return new RefreshEventHandler() {
			@Override
			public void handleEvent(RefreshEvent result) {
				if (result.getRefreshType() == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadPlayerMiscCommands();
				}
			}
		};
	}
	private UndoEventHandler createUndoEventHandler(){
		return new UndoEventHandler() {
			@Override
			public void handleEvent(UndoEvent event) {
				loadPlayerMiscCommands();
			}
		};
	}
	


	private ChallengeEventHandler createChallengeEventHandler() {
		return new ChallengeEventHandler() {
			@Override
			public void handleEvent(ChallengeEvent event) {
				loadPlayerMiscCommands();
			}
		};
	}

	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				loadPlayerMiscCommands();
			}
		};
	}
}
