package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.score.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class ScorePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final CommandClearScoresCallback eventCommandClearScoresCallback = createCommandClearScoresCallback();
	final UpdatePlayerScoreListCallback eventChangedScoresCallback = createUpdatePlayerScoreListCallback();
	final ClickHandler clickCleanScoresHandler = createClickCleanScoresHandler();
	final CommandRefreshCallback eventCommandRefreshCallback = createCommandRefreshCallback();
	final CommandUndoCallback eventCommandUndoCallback = createCommandUndoCallback();
	final CommandPlayerWonCallback eventCommandPlayerWonCallback = createCommandPlayerWonCallback();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

	public interface Display {
		void addCleanScoresHandler(ClickHandler clickHandler);

		void changeScoreList(List<ScoreTableRow> content);
	}

	public ScorePresenter(Display scoreView, LoginDataDto loginData, EventBus eventBus) {
		this.view = scoreView;
		this.loginData = loginData;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
		initEvents();
		loadScores();
	}

	private void initView() {
		view.addCleanScoresHandler(clickCleanScoresHandler);

	}

	private void initEvents() {
		eventBus.addHandler(eventCommandClearScoresCallback);
		eventBus.addHandler(eventChangedScoresCallback);
		eventBus.addHandler(eventCommandRefreshCallback);
		eventBus.addHandler(eventCommandUndoCallback);
		eventBus.addHandler(eventCommandPlayerWonCallback);
		eventBus.addHandler(eventCommandChangeNicknameCallback);
	}

	private void loadScores() {
		eventBus.updatePlayerScoreList();
	}

	private ClickHandler createClickCleanScoresHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.clearAllScores(loginData.player);
			}
		};
	}

	private CommandClearScoresCallback createCommandClearScoresCallback() {
		return new CommandClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadScores();
			}
		};
	}

	private UpdatePlayerScoreListCallback createUpdatePlayerScoreListCallback() {
		return new UpdatePlayerScoreListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerScoreDto> scores) {
				List<ScoreTableRow> content = new ArrayList<ScoreTableRow>();
				for (PlayerScoreDto playerScoreDto : scores) {
					boolean currentUser = playerScoreDto.player.email.address.equals(loginData.player.email.address);
					content.add(new ScoreTableRow(playerScoreDto.player, "" + playerScoreDto.score, ""
							+ playerScoreDto.ranking, playerScoreDto.changedDateTime,
							playerScoreDto.changedBy.nickname, currentUser));
				}
				view.changeScoreList(content);
			}
		};
	}

	private CommandRefreshCallback createCommandRefreshCallback() {
		return new CommandRefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadScores();
			}
		};
	}

	private CommandUndoCallback createCommandUndoCallback() {
		return new CommandUndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				loadScores();
			}
		};
	}

	private CommandPlayerWonCallback createCommandPlayerWonCallback() {
		return new CommandPlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadScores();
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				loadScores();
			}

		};
	}
}
