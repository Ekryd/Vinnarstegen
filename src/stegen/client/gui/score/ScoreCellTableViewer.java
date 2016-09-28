package stegen.client.gui.score;

import java.util.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class ScoreCellTableViewer implements IsWidget {
	private final CellTable<ScoreTableRow> baseWidget = new CellTable<ScoreTableRow>();
	private final ListDataProvider<ScoreTableRow> dataProvider = new ListDataProvider<ScoreTableRow>();;

	public ScoreCellTableViewer() {
		initLayout();
		initTextColumns();
		initLayoutColumns();
		addDataProvider();
	}

	private void initLayout() {
		baseWidget.setVisibleRange(0, 25);
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


	private void initLayoutColumns() {
		baseWidget.addColumnStyleName(0, "playerColumn");
		baseWidget.addColumnStyleName(3, "dateColumn");
		baseWidget.addColumnStyleName(4, "playerColumn");
	}

	private void addDataProvider() {
		dataProvider.addDataDisplay(baseWidget);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}


	public void changeContent(List<ScoreTableRow> scores) {
		List<ScoreTableRow> tableList = dataProvider.getList();
		tableList.clear();
		for (ScoreTableRow playerScore : scores) {
			tableList.add(playerScore);
		}
	}
}
