package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.playeraction.*;
import stegen.shared.*;

public class GameResultsPresenter implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	final UpdateGameResultListCallback eventUpdateGameResultListCallback = creatUpdateGameResultListCallback();
	final RefreshCallback refreshCallback = createRefreshCallback();
	final UndoCallback undoCallback = createUndoCallback();
	final PlayerWonCallback playerWonCallback = createPlayerWonCallback();
	final ClearScoresCallback eventClearScoresCallback = createClearScoresCallback();

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
		eventBus.addHandler(refreshCallback);
		eventBus.addHandler(undoCallback);
		eventBus.addHandler(playerWonCallback);
		eventBus.addHandler(eventClearScoresCallback);
	}

	private void loadGameResults() {
		eventBus.updateGameResultList();
	}

	private UpdateGameResultListCallback creatUpdateGameResultListCallback() {
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

	private RefreshCallback createRefreshCallback() {
		return new RefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadGameResults();
			}
		};
	}

	private UndoCallback createUndoCallback() {
		return new UndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				loadGameResults();
			}
		};
	}

	private PlayerWonCallback createPlayerWonCallback() {
		return new PlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadGameResults();
			}
		};
	}

	private ClearScoresCallback createClearScoresCallback() {
		return new ClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadGameResults();
			}
		};
	}

}
