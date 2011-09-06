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
	final ClearScoresCallback eventClearScoresCallback = createClearScoresCallback();
	final UpdatePlayerScoreListCallback eventChangedScoresCallback = creatEventChangedScoresCallback();
	final ClickHandler clickCleanScoresHandler = createClickCleanScoresHandler();
	final RefreshCallback eventRefreshCallback = createRefreshCallback();
	final UndoCallback eventUndoCallback = createUndoCallback();
	final PlayerWonCallback eventPlayerWonCallback = createPlayerWonCallback();
	final ChangeNicknameCallback eventChangeNicknameCallback = createChangeNicknameCallback();

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
		eventBus.addHandler(eventClearScoresCallback);
		eventBus.addHandler(eventChangedScoresCallback);
		eventBus.addHandler(eventRefreshCallback);
		eventBus.addHandler(eventUndoCallback);
		eventBus.addHandler(eventPlayerWonCallback);
		eventBus.addHandler(eventChangeNicknameCallback);
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

	private ClearScoresCallback createClearScoresCallback() {
		return new ClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadScores();
			}
		};
	}

	private UpdatePlayerScoreListCallback creatEventChangedScoresCallback() {
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

	private RefreshCallback createRefreshCallback() {
		return new RefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadScores();
			}
		};
	}

	private UndoCallback createUndoCallback() {
		return new UndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				loadScores();
			}
		};
	}

	private PlayerWonCallback createPlayerWonCallback() {
		return new PlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadScores();
			}
		};
	}

	private ChangeNicknameCallback createChangeNicknameCallback() {
		return new ChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(PlayerDto result) {
				loadScores();
			}

		};
	}
}
