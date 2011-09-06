package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.playeraction.*;
import stegen.shared.*;

public class PlayerMiscCommandsPresenter implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	final UpdatePlayerMiscCommandListCallback eventUpdatePlayerMiscCommandListCallback = creatUpdatePlayerMiscCommandListCallback();
	final RefreshCallback eventRefreshCallback = createRefreshCallback();
	final UndoCallback eventUndoCallback = createUndoCallback();
	final ChallengeCallback eventChallengeCallback = createChallengeCallback();
	final ChangeNicknameCallback eventChangeNicknameCallback = createChangeNicknameCallback();

	public interface Display {
		void changePlayerMiscCommandList(List<PlayerMiscCommandRow> content);
	}

	public PlayerMiscCommandsPresenter(Display scoreView, EventBus eventBus) {
		this.view = scoreView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadPlayerMiscCommands();
	}

	private void initEvents() {
		eventBus.addHandler(eventUpdatePlayerMiscCommandListCallback);
		eventBus.addHandler(eventRefreshCallback);
		eventBus.addHandler(eventUndoCallback);
		eventBus.addHandler(eventChallengeCallback);
		eventBus.addHandler(eventChangeNicknameCallback);
	}

	private void loadPlayerMiscCommands() {
		eventBus.updatePlayerMiscCommandList();
	}

	private UpdatePlayerMiscCommandListCallback creatUpdatePlayerMiscCommandListCallback() {
		return new UpdatePlayerMiscCommandListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerCommandDto> gameResults) {
				List<PlayerMiscCommandRow> content = new ArrayList<PlayerMiscCommandRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new PlayerMiscCommandRow(playerCommandDto.player.nickname, playerCommandDto.description));
				}
				view.changePlayerMiscCommandList(content);
			}
		};
	}

	private RefreshCallback createRefreshCallback() {
		return new RefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadPlayerMiscCommands();
			}
		};
	}

	private UndoCallback createUndoCallback() {
		return new UndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				loadPlayerMiscCommands();
			}
		};
	}

	private ChallengeCallback createChallengeCallback() {
		return new ChallengeCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadPlayerMiscCommands();
			}
		};
	}

	private ChangeNicknameCallback createChangeNicknameCallback() {
		return new ChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(PlayerDto result) {
				loadPlayerMiscCommands();
			}

		};
	}

}
