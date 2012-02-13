package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.score.*;
import stegen.shared.*;

public class ScorePresenterViewer implements Presenter {
	private final Display view;
	private final EventBus eventBus;
	final UpdatePlayerScoreListCallback eventChangedScoresCallback = createUpdatePlayerScoreListCallback();

	public interface Display {
		void changeScoreList(List<ScoreTableRow> content);
	}

	public ScorePresenterViewer(Display scoreView, EventBus eventBus) {
		this.view = scoreView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadScores();
	}

	private void initEvents() {
		eventBus.addHandler(eventChangedScoresCallback);
	}

	private void loadScores() {
		eventBus.updatePlayerScoreList(null); // loginData.player.email
	}



	private UpdatePlayerScoreListCallback createUpdatePlayerScoreListCallback() {
		return new UpdatePlayerScoreListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerScoreDto> scores) {
				List<ScoreTableRow> content = new ArrayList<ScoreTableRow>();
				for (PlayerScoreDto playerScoreDto : scores) {
					boolean currentUser = playerScoreDto.player.email.address.equals(null); // loginData.player.email.address
					content.add(new ScoreTableRow(playerScoreDto.player, "" + playerScoreDto.score, ""
							+ playerScoreDto.ranking, playerScoreDto.changedDateTime,
							playerScoreDto.changedBy.nickname, currentUser, playerScoreDto.showChallenge));
				}
				view.changeScoreList(content);
			}
		};
	}


}
