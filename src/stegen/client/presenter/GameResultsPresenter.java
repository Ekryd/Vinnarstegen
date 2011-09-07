package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.playeraction.*;
import stegen.shared.*;

public class GameResultsPresenter implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	final UpdateGameResultListCallback eventUpdateGameResultListCallback = createUpdateGameResultListCallback();
	final CommandRefreshCallback eventCommandRefreshCallback = createCommandRefreshCallback();
	final CommandUndoCallback eventCommandUndoCallback = createCommandUndoCallback();
	final CommandPlayerWonCallback eventCommandPlayerWonCallback = createCommandPlayerWonCallback();
	final CommandClearScoresCallback eventCommandClearScoresCallback = createCommandClearScoresCallback();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

	public interface Display {
		void changeGameResultList(List<GameResultsRow> content);
	}

	public GameResultsPresenter(Display scoreView, EventBus eventBus) {
		this.view = scoreView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadGameResults();
	}

	private void initEvents() {
		eventBus.addHandler(eventUpdateGameResultListCallback);
		eventBus.addHandler(eventCommandRefreshCallback);
		eventBus.addHandler(eventCommandUndoCallback);
		eventBus.addHandler(eventCommandPlayerWonCallback);
		eventBus.addHandler(eventCommandClearScoresCallback);
		eventBus.addHandler(eventCommandChangeNicknameCallback);
	}

	private void loadGameResults() {
		eventBus.updateGameResultList();
	}

	private UpdateGameResultListCallback createUpdateGameResultListCallback() {
		return new UpdateGameResultListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerCommandDto> gameResults) {
				List<GameResultsRow> content = new ArrayList<GameResultsRow>();
				for (PlayerCommandDto playerCommandDto : gameResults) {
					content.add(new GameResultsRow(playerCommandDto.player.nickname,
							playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeGameResultList(content);
			}
		};
	}

	private CommandRefreshCallback createCommandRefreshCallback() {
		return new CommandRefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadGameResults();
			}
		};
	}

	private CommandUndoCallback createCommandUndoCallback() {
		return new CommandUndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				loadGameResults();
			}
		};
	}

	private CommandPlayerWonCallback createCommandPlayerWonCallback() {
		return new CommandPlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadGameResults();
			}
		};
	}

	private CommandClearScoresCallback createCommandClearScoresCallback() {
		return new CommandClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadGameResults();
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(PlayerDto result) {
				loadGameResults();
			}

		};
	}

}
