package stegen.client.gui.score;

import java.util.*;

import stegen.client.gui.challenge.*;
import stegen.client.gui.gameresult.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class ScoreCellTable2 implements IsWidget {
	private final CellTable<ScoreTableRow> baseWidget = new CellTable<ScoreTableRow>();
	private final ListDataProvider<ScoreTableRow> dataProvider = new ListDataProvider<ScoreTableRow>();;
	private final Column<ScoreTableRow, String> winColumn = new ButtonColumn2("Jag vann mot");
	private final Column<ScoreTableRow, String> loseColumn = new ButtonColumn2("Jag förlorade mot");
	private final Column<ScoreTableRow, String> challengeColumn = new ButtonColumn2("Utmana!");
	private final WinGameDialogBox2 gameResultDialogBox = new WinGameDialogBox2();
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
				return cell.player.nickname;
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
		baseWidget.addColumn(loseColumn);
		baseWidget.addColumn(challengeColumn);
	}

	private void addDataProvider() {
		dataProvider.addDataDisplay(baseWidget);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

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

	public Column<ScoreTableRow, String> getWinnerColumn() {
		return winColumn;
	}

	public Column<ScoreTableRow, String> getLoserColumn() {
		return loseColumn;
	}

	public WinGameDialogBox2 getWinGameDialog() {
		return gameResultDialogBox;
	}

}
