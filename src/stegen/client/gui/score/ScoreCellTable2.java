package stegen.client.gui.score;

import java.util.*;

import stegen.client.gui.challenge.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class ScoreCellTable2 implements IsWidget {
	private final CellTable<ScoreTableRow> baseWidget = new CellTable<ScoreTableRow>();
	private final ListDataProvider<ScoreTableRow> dataProvider = new ListDataProvider<ScoreTableRow>();;
	private final Column<ScoreTableRow, String> winColumn = new ButtonColumn2("Jag vann mot");
	private final Column<ScoreTableRow, String> loseColumn = new ButtonColumn2("Jag förlorade mot");
	private final Column<ScoreTableRow, String> challengeColumn = new ButtonColumn2("Utmana!");
	// private final GameResultDialogBox2 gameResultDialogBox;
	private final ChallengeDialog2 challengeDialog = new ChallengeDialog2();

	public ScoreCellTable2() {
		initTextColumns();
		initButtonColumns();
		addDataProvider();
	}

	private void initTextColumns() {
		baseWidget.addColumn(new TextColumn<ScoreTableRow>() {

			@Override
			public String getValue(ScoreTableRow cell) {
				return cell.name;
			}
		}, "Namn");
		baseWidget.addColumn(new TextColumn<ScoreTableRow>() {

			@Override
			public String getValue(ScoreTableRow cell) {
				return cell.score;
			}
		}, "Poäng");
		baseWidget.addColumn(new TextColumn<ScoreTableRow>() {

			@Override
			public String getValue(ScoreTableRow cell) {
				return cell.ranking;
			}
		}, "Rankad");
		baseWidget.addColumn(new TextColumn<ScoreTableRow>() {

			@Override
			public String getValue(ScoreTableRow cell) {
				return cell.changedDateTime;
			}
		}, "Senast ändrad");
		baseWidget.addColumn(new TextColumn<ScoreTableRow>() {

			@Override
			public String getValue(ScoreTableRow cell) {
				return cell.changedBy;
			}
		}, "Ändrad av");

	}

	private void initButtonColumns() {

		baseWidget.addColumn(winColumn);
		// winColumn.setFieldUpdater(new FieldUpdater<ScoreCell, String>() {
		//
		// @Override
		// public void update(int index, ScoreCell cell, String value) {
		// playerWonOverPlayer(loginData.player, playerScore.player);
		// }
		// });
		baseWidget.addColumn(loseColumn);
		// loseColumn.setFieldUpdater(new FieldUpdater<ScoreCell, String>() {
		//
		// @Override
		// public void update(int index, ScoreCell cell, String value) {
		// playerWonOverPlayer(playerScore.player, loginData.player);
		// }
		// });
		baseWidget.addColumn(challengeColumn);
	}

	private void addDataProvider() {
		dataProvider.addDataDisplay(baseWidget);
	}

	// public void setUpdaterWonGameHandler(FieldUpdater<ScoreCell, String>
	// fieldUpdater) {
	// winColumn.setFieldUpdater(fieldUpdater);
	// }
	//
	// public void setUpdaterLostGameHandler(FieldUpdater<ScoreCell, String>
	// fieldUpdater) {
	// loseColumn.setFieldUpdater(fieldUpdater);
	// }
	//
	// public void setUpdaterChallengeHandler(FieldUpdater<ScoreCell, String>
	// fieldUpdater) {
	// challengeColumn.setFieldUpdater(fieldUpdater);
	// }

	public Column<ScoreTableRow, String> getChallengeColumn() {
		return challengeColumn;
	}

	public ChallengeDialog2 getChallengeDialog() {
		return challengeDialog;
	}

	public void changeContent(List<ScoreTableRow> scores) {
		List<ScoreTableRow> tableList = dataProvider.getList();
		tableList.clear();
		for (ScoreTableRow playerScore : scores) {
			tableList.add(playerScore);
		}
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

}
