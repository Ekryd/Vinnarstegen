package stegen.client.gui.score;

import java.util.*;

import stegen.client.gui.challenge.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class ScoreCellTable2 implements IsWidget {
	public interface ScoreCell {
		String getName();

		String getScore();

		String getRanking();

		String getChangedDateTime();

		String getChangedBy();

		boolean isCurrentUser();
	}

	private final CellTable<ScoreCell> baseWidget = new CellTable<ScoreCell>();
	private final ListDataProvider<ScoreCell> dataProvider = new ListDataProvider<ScoreCell>();;
	private final Column<ScoreCell, String> winColumn = new ButtonColumn2("Jag vann mot");
	private final Column<ScoreCell, String> loseColumn = new ButtonColumn2("Jag förlorade mot");
	private final Column<ScoreCell, String> challengeColumn = new ButtonColumn2("Utmana!");
	// private final GameResultDialogBox2 gameResultDialogBox;
	private final ChallengeDialog2 challengeDialog = new ChallengeDialog2();

	public ScoreCellTable2() {
		initTextColumns();
		initButtonColumns();
		addDataProvider();
	}

	private void initTextColumns() {
		baseWidget.addColumn(new TextColumn<ScoreCell>() {

			@Override
			public String getValue(ScoreCell cell) {
				return cell.getName();
			}
		}, "Namn");
		baseWidget.addColumn(new TextColumn<ScoreCell>() {

			@Override
			public String getValue(ScoreCell cell) {
				return cell.getScore();
			}
		}, "Poäng");
		baseWidget.addColumn(new TextColumn<ScoreCell>() {

			@Override
			public String getValue(ScoreCell cell) {
				return cell.getRanking();
			}
		}, "Rankad");
		baseWidget.addColumn(new TextColumn<ScoreCell>() {

			@Override
			public String getValue(ScoreCell cell) {
				return cell.getChangedDateTime();
			}
		}, "Senast ändrad");
		baseWidget.addColumn(new TextColumn<ScoreCell>() {

			@Override
			public String getValue(ScoreCell cell) {
				return cell.getChangedBy();
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

	public Column<ScoreCell, String> getChallengeColumn() {
		return challengeColumn;
	}

	public ChallengeDialog2 getChallengeDialog() {
		return challengeDialog;
	}

	public void changeContent(List<ScoreCell> scores) {
		List<ScoreCell> tableList = dataProvider.getList();
		tableList.clear();
		for (ScoreCell playerScore : scores) {
			tableList.add(playerScore);
		}
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

}
