package stegen.client.gui.gameresult;

import stegen.client.dto.*;
import stegen.client.gui.common.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.client.ui.*;

public class GameResultDialogBox extends DialogBox {
	private PlayerDto winner;
	private PlayerDto loser;
	private final GameResultDto gameResult = GameResultDto.createEmptyGameResult();
	private final Label scoreLabel = new Label();
	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private final CancelOrOkButtonPanel buttonPanel;
	private final SetScoreDropdown setScoreDropdown;

	public GameResultDialogBox(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		this.buttonPanel = createButtonPanel();
		this.setScoreDropdown = createDropDown();
		init();
	}

	private CancelOrOkButtonPanel createButtonPanel() {
		return new CancelOrOkButtonPanel() {

			@Override
			protected void onOkButtonClick() {
				hide();
				messageCentral.playerWonOverPlayer(winner, loser, gameResult, loginData.player);
			}

			@Override
			protected void onCloseButtonClick() {
				hide();
			}
		};
	}

	private SetScoreDropdown createDropDown() {
		return new SetScoreDropdown(gameResult, new UpdateScoreCallback() {

			@Override
			public void onScoreChange() {
				updateScoreLabel();
			}
		});
	}

	private void init() {
		setText("Matchresultat");
		setAnimationEnabled(true);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new HTML("<p>Skriv in matchresultatet. Vinnaren står först</p>"));
		// Widget gameSetResultTable = new GameSetResultTable(new
		// UpdateScoreCallback() {
		//
		// @Override
		// public void onScoreChange() {
		// updateScoreLabel();
		// }
		// }, winnerEmail, loserEmail, gameResult);
		// verticalPanel.add(gameSetResultTable);

		verticalPanel.add(setScoreDropdown);

		updateScoreLabel();
		verticalPanel.add(scoreLabel);

		verticalPanel.add(buttonPanel);
		setWidget(verticalPanel);
	}

	private void updateScoreLabel() {
		GameResultCalculator calculator = new GameResultCalculator(gameResult);
		int win = calculator.getWonSets();
		int lose = calculator.getLostSets();
		scoreLabel.setText(win + " - " + lose);
		if (lose >= win) {
			buttonPanel.setOkButtonEnabled(false);
			scoreLabel.setStylePrimaryName("score_big_error");
		} else {
			buttonPanel.setOkButtonEnabled(true);
			scoreLabel.setStylePrimaryName("score_big");
		}
	}

	public void setPlayers(PlayerDto winner, PlayerDto loser) {
		this.winner = winner;
		this.loser = loser;
		setScoreDropdown.setPlayers(winner, loser);
	}

}
