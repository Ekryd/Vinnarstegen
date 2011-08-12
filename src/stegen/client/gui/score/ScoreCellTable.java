package stegen.client.gui.score;

import java.util.*;

import stegen.client.gui.challenge.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.view.client.*;

public class ScoreCellTable extends CellTable<PlayerScoreDto> implements ScoreListener {

	private final LoginDataDto loginData;
	private ListDataProvider<PlayerScoreDto> dataProvider;
	private final GameResultDialogBox gameResultDialogBox;
	private final ChallengeDialog challengeDialog;

	public ScoreCellTable(MessageCentral messageCentral, final LoginDataDto loginData) {
		this.loginData = loginData;
		this.gameResultDialogBox = new GameResultDialogBox(messageCentral, loginData);
		this.challengeDialog = new ChallengeDialog(messageCentral, loginData);
		initTextColumns();
		initButtonColumns();
		addDataProvider();
		messageCentral.listeners.addListener(this);
	}

	private void initTextColumns() {
		addColumn(new TextColumn<PlayerScoreDto>() {

			@Override
			public String getValue(PlayerScoreDto player) {
				return player.player.nickname;
			}
		}, "Namn");
		addColumn(new TextColumn<PlayerScoreDto>() {

			@Override
			public String getValue(PlayerScoreDto player) {
				return "" + player.score;
			}
		}, "Poäng");
		addColumn(new TextColumn<PlayerScoreDto>() {

			@Override
			public String getValue(PlayerScoreDto player) {
				return "" + player.ranking;
			}
		}, "Rankad");
		addColumn(new TextColumn<PlayerScoreDto>() {

			@Override
			public String getValue(PlayerScoreDto player) {
				return player.changedDateTime;
			}
		}, "Senast ändrad");
		addColumn(new TextColumn<PlayerScoreDto>() {

			@Override
			public String getValue(PlayerScoreDto player) {
				return player.player.nickname;
			}
		}, "Ändrad av");

	}

	private void initButtonColumns() {
		Column<PlayerScoreDto, String> winColumn = new ButtonColumn(loginData, "Jag vann mot");

		addColumn(winColumn);
		winColumn.setFieldUpdater(new FieldUpdater<PlayerScoreDto, String>() {

			@Override
			public void update(int index, PlayerScoreDto playerScore, String value) {
				playerWonOverPlayer(loginData.player, playerScore.player);
			}
		});
		Column<PlayerScoreDto, String> loseColumn = new ButtonColumn(loginData, "Jag förlorade mot");
		addColumn(loseColumn);
		loseColumn.setFieldUpdater(new FieldUpdater<PlayerScoreDto, String>() {

			@Override
			public void update(int index, PlayerScoreDto playerScore, String value) {
				playerWonOverPlayer(playerScore.player, loginData.player);
			}
		});
		Column<PlayerScoreDto, String> challengeColumn = new ButtonColumn(loginData, "Utmana!");
		addColumn(challengeColumn);
		challengeColumn.setFieldUpdater(new FieldUpdater<PlayerScoreDto, String>() {

			@Override
			public void update(int index, PlayerScoreDto playerScore, String value) {
				challenge(playerScore.player);
			}
		});
	}

	private void challenge(PlayerDto challengee) {
		challengeDialog.setChallengeeAndInitDialog(challengee);
		challengeDialog.center();
	}

	public void addDataProvider() {
		dataProvider = new ListDataProvider<PlayerScoreDto>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(this);

	}

	private void playerWonOverPlayer(final PlayerDto winner, final PlayerDto loser) {
		gameResultDialogBox.setPlayers(winner, loser);
		gameResultDialogBox.center();
	}

	@Override
	public void onScoreChange(List<PlayerScoreDto> result) {
		List<PlayerScoreDto> tableList = dataProvider.getList();
		tableList.clear();
		for (PlayerScoreDto playerScore : result) {
			tableList.add(playerScore);
		}
	}
}
