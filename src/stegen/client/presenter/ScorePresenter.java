package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.score.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class ScorePresenter implements Presenter {
	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	final ClearScoresCallback eventClearScoresCallback = createClearScoresCallback();
	final UpdatePlayerScoreListCallback eventChangedScoresCallback = creatEventChangedScoresCallback();
	final ClickHandler clickCleanScoresHandler = createClickCleanScoresHandler();

	public interface Display {
		void addCleanScoresHandler(ClickHandler clickHandler);

		void changeScoreList(List<ScoreTableRow> content);
	}

	public ScorePresenter(Display scoreView, LoginDataDto result, EventBus eventBus) {
		this.view = scoreView;
		this.result = result;
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
	}

	private void loadScores() {
		eventBus.updatePlayerScoreList();
	}

	private ClickHandler createClickCleanScoresHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.clearAllScores(result.player);
			}
		};
	}

	private ClearScoresCallback createClearScoresCallback() {
		return new ClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				eventBus.updatePlayerScoreList();
			}
		};
	}

	private UpdatePlayerScoreListCallback creatEventChangedScoresCallback() {
		return new UpdatePlayerScoreListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerScoreDto> scores) {
				List<ScoreTableRow> content = new ArrayList<ScoreTableRow>();
				for (PlayerScoreDto playerScoreDto : scores) {
					boolean currentUser = playerScoreDto.player.email.equals(result.player.email);
					content.add(new ScoreTableRow(playerScoreDto.player.nickname, "" + playerScoreDto.score, ""
							+ playerScoreDto.ranking, playerScoreDto.changedDateTime,
							playerScoreDto.changedBy.nickname, currentUser));
				}
				view.changeScoreList(content);
			}
		};
	}

}
