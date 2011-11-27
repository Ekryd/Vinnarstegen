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
	final UpdateRefreshCallback eventCommandRefreshCallback = createRefreshCallback();
	final CommandUndoCallback eventCommandUndoCallback = createCommandUndoCallback();
	final CommandChallengeCallback eventCommandChallengeCallback = createCommandChallengeCallback();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

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
		eventBus.addHandler(eventCommandRefreshCallback);
		eventBus.addHandler(eventCommandUndoCallback);
		eventBus.addHandler(eventCommandChallengeCallback);
		eventBus.addHandler(eventCommandChangeNicknameCallback);
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
					content.add(new PlayerMiscCommandRow(playerCommandDto.player.nickname,
							playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changePlayerMiscCommandList(content);
			}
		};
	}

	private UpdateRefreshCallback createRefreshCallback() {
		return new UpdateRefreshCallback() {

			@Override
			public void onSuccessImpl(RefreshType result) {
				if (result == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadPlayerMiscCommands();
				}
			}
		};
	}

	private CommandUndoCallback createCommandUndoCallback() {
		return new CommandUndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				loadPlayerMiscCommands();
			}
		};
	}

	private CommandChallengeCallback createCommandChallengeCallback() {
		return new CommandChallengeCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadPlayerMiscCommands();
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				loadPlayerMiscCommands();
			}

		};
	}

}
