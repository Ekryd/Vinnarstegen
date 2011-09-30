package stegen.client.gui.score;

import java.util.*;

import stegen.client.gui.challenge.*;
import stegen.client.gui.gameresult.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;

public class ListScorePanel implements IsWidget {

	private final ScrollPanel baseWidget = new ScrollPanel();
	private final VerticalPanel panel = new VerticalPanel();
	private final CleanScoresButton cleanScoresButton = new CleanScoresButton();
	private final ScoreCellTable scoreCellTable = new ScoreCellTable();

	public ListScorePanel() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.add(panel);
		panel.add(scoreCellTable);
		panel.add(cleanScoresButton);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void addCleanScoresHandler(ClickHandler clickHandler) {
		cleanScoresButton.addClickCleanScoresHandler(clickHandler);
	}

	public void changeScoreList(List<ScoreTableRow> content) {
		scoreCellTable.changeContent(content);
	}

	public Column<ScoreTableRow, String> getChallengeButtonColumn() {
		return scoreCellTable.getChallengeColumn();
	}

	public ChallengeDialog getChallengeDialog() {
		return scoreCellTable.getChallengeDialog();
	}

	public Column<ScoreTableRow, String> getWinnerButtonColumn() {
		return scoreCellTable.getWinnerColumn();
	}

	public Column<ScoreTableRow, String> getLoserButtonColumn() {
		return scoreCellTable.getLoserColumn();
	}

	public WinGameDialogBox getWinGameDialog() {
		return scoreCellTable.getWinGameDialog();
	}

}
