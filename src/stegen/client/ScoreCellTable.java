package stegen.client;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.view.client.*;

public class ScoreCellTable extends CellTable<PlayerScoreDto> implements ScoreListener {

	private final LoginDataDto loginData;
	private final MessageCentral messageCentral;
	private ListDataProvider<PlayerScoreDto> dataProvider;

	public ScoreCellTable(MessageCentral messageCentral, final LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		initTextColumns();
		initButtonColumns();
		addDataProvider();
		messageCentral.addListener(this);
	}

	private void initTextColumns() {
		addColumn(new TextColumn<PlayerScoreDto>() {

			@Override
			public String getValue(PlayerScoreDto player) {
				return player.email.address;
			}
		}, "Email");
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
				return player.changedBy.address;
			}
		}, "Ändrad av");

	}

	private void initButtonColumns() {
		Column<PlayerScoreDto, String> winColumn = new ButtonColumn(loginData, "Jag vann mot");
		addColumn(winColumn);
		winColumn.setFieldUpdater(new FieldUpdater<PlayerScoreDto, String>() {

			@Override
			public void update(int index, PlayerScoreDto playerScore, String value) {
				playerWonOverPlayer(loginData.emailAddress, playerScore.email);
			}
		});
		Column<PlayerScoreDto, String> loseColumn = new ButtonColumn(loginData, "Jag förlorade mot");
		addColumn(loseColumn);
		loseColumn.setFieldUpdater(new FieldUpdater<PlayerScoreDto, String>() {

			@Override
			public void update(int index, PlayerScoreDto playerScore, String value) {
				playerWonOverPlayer(playerScore.email, loginData.emailAddress);
			}
		});
	}

	public void addDataProvider() {
		dataProvider = new ListDataProvider<PlayerScoreDto>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(this);

	}

	private void playerWonOverPlayer(final EmailAddressDto winEmail, final EmailAddressDto loseEmail) {
		if (winEmail.equals(loseEmail)) {
			return;
		}
		new GameResultDialogBox(winEmail, loseEmail) {

			@Override
			protected void sendGameResult(GameResultDto gameResult) {
				messageCentral.playerWonOverPlayer(winEmail, loseEmail, gameResult, loginData.emailAddress);
			}
		}.center();
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
