package stegen.client.gui.score;

import java.util.*;


import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class ListScorePanel2 implements IsWidget {

	private final ScrollPanel baseWidget = new ScrollPanel();
	private final VerticalPanel panel = new VerticalPanel();
	private final CleanScoresButton2 cleanScoresButton = new CleanScoresButton2();
	private final ScoreCellTable2 scoreCellTable = new ScoreCellTable2();

	public ListScorePanel2() {
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

}
