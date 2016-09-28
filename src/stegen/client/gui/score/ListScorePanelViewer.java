package stegen.client.gui.score;

import java.util.*;

import com.google.gwt.user.client.ui.*;

public class ListScorePanelViewer implements IsWidget {

	private final VerticalPanel baseWidget = new VerticalPanel();
	private final ScoreCellTableViewer scoreCellTable = new ScoreCellTableViewer();

	public ListScorePanelViewer() {
		initLayout();
	}

	private void initLayout() {
		// Stops FireFox from expanding the table
		baseWidget.setHeight("1px");
		baseWidget.add(scoreCellTable);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void changeScoreList(List<ScoreTableRow> content) {
		scoreCellTable.changeContent(content);
	}
}
