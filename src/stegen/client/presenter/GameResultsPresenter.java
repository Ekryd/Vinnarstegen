package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.gui.playeraction.*;
import stegen.client.service.*;
import stegen.shared.*;

public class GameResultsPresenter implements Presenter {
	private final Display view;
	private final PlayerCommandServiceAsync playerCommandService;
	final com.google.gwt.event.shared.EventBus gwtEventBus;
	final AbstractAsyncCallback<List<PlayerCommandDto>> eventUpdateGameResultListCallback = createUpdateGameResultListCallback();
	final RefreshEventHandler refreshEventHandler = refreshEventHandler();
	final UndoEventHandler undoEventHandler = createUndoEventHandler();
	final GamePlayedEventHandler gamePlayedEventHandler = createGamePlayedEventHandler();
	final ClearScoresEventHandler clearScoresEventHandler = createClearScoresEventHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler =  createChangeNicknameEventHandler();

	public interface Display {
		void changeGameResultList(List<GameResultsRow> content);
	}

	public GameResultsPresenter(Display scoreView,com.google.gwt.event.shared.EventBus gwtEventBus,PlayerCommandServiceAsync playerCommandService) {
		this.view = scoreView;
		this.gwtEventBus = gwtEventBus;
		this.playerCommandService =playerCommandService;
	}

	@Override
	public void go() {
		initEvents();
		loadGameResults();
	}

	private void initEvents() {
		gwtEventBus.addHandler(RefreshEvent.TYPE,refreshEventHandler);
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
		gwtEventBus.addHandler(GamePlayedEvent.TYPE, gamePlayedEventHandler);
		gwtEventBus.addHandler(UndoEvent.TYPE, undoEventHandler);
		gwtEventBus.addHandler(ClearScoresEvent.TYPE,clearScoresEventHandler);
	}
	
	
	private RefreshEventHandler refreshEventHandler(){
		return new RefreshEventHandler() {
			@Override
			public void handleEvent(RefreshEvent event) {
				if (event.getRefreshType() == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadGameResults();
				}
			}
		};
	}

	private void loadGameResults() {
		playerCommandService.getGameResultCommandStack(10,eventUpdateGameResultListCallback);
	}

	private AbstractAsyncCallback<List<PlayerCommandDto>> createUpdateGameResultListCallback() {
		return new AbstractAsyncCallback<List<PlayerCommandDto>>() {

			@Override
			public void onSuccess(List<PlayerCommandDto> gameResults) {
				List<GameResultsRow> content = new ArrayList<GameResultsRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new GameResultsRow(playerCommandDto.player.nickname,playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeGameResultList(content);
			}
		};
	}

	private UndoEventHandler createUndoEventHandler(){
		return new UndoEventHandler() {
			@Override
			public void handleEvent(UndoEvent event) {
				loadGameResults();
			}
		};
	}
	
	private GamePlayedEventHandler createGamePlayedEventHandler(){
		return new GamePlayedEventHandler() {
			@Override
			public void handleEvent(GamePlayedEvent event) {
				loadGameResults();
			}
		};
	}

	private ClearScoresEventHandler createClearScoresEventHandler() {
		return new ClearScoresEventHandler() {
			
			@Override
			public void handleEvent(ClearScoresEvent event) {
				loadGameResults();
			}
		};
	}

	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				loadGameResults();
			}
		};
	}
}
