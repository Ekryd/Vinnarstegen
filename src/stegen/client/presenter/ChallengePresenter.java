package stegen.client.presenter;

import stegen.client.event.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class ChallengePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;

	public interface Display {
		void addClickOpenChallengeInputHandler(ClickHandler clickHandler);

		void addClickSendChallengeHandler(ClickHandler clickHandler);
	}

	public ChallengePresenter(Display scoreView, LoginDataDto result, EventBus eventBus) {
		this.view = scoreView;
		this.result = result;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		// initView();
		// initEvents();
		// loadScores();
	}

	// private void initEvents() {
	// eventBus.addHandler(eventPlayerWonCallback);
	// eventBus.addHandler(eventChangedScoresCallback);
	// }
	//
	// private void loadScores() {
	// eventBus.updatePlayerScoreList();
	// }
	//
	// private PlayerWonCallback createPlayerWonCallback() {
	// return new PlayerWonCallback() {
	//
	// @Override
	// public void onSuccessImpl(Void result) {
	// eventBus.updatePlayerScoreList();
	// }
	// };
	// }
	//
	// private UpdatePlayerScoreListCallback creatEventChangedScoresCallback() {
	// return new UpdatePlayerScoreListCallback() {
	//
	// @Override
	// public void onSuccessImpl(List<PlayerScoreDto> scores) {
	// List<ScoreCell> content = new ArrayList<ScoreCell>();
	// for (PlayerScoreDto playerScoreDto : scores) {
	// boolean currentUser =
	// playerScoreDto.player.email.equals(result.player.email);
	// content.add(new ScoreCellImpl(playerScoreDto.player.nickname, "" +
	// playerScoreDto.score, ""
	// + playerScoreDto.ranking, playerScoreDto.changedDateTime,
	// playerScoreDto.changedBy.nickname, currentUser));
	// }
	// view.changeScoreList(content);
	// }
	// };
	// }

}
