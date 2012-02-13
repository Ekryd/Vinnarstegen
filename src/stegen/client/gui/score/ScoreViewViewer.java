package stegen.client.gui.score;

import java.util.*;

import stegen.client.presenter.ScorePresenterViewer.Display;

public class ScoreViewViewer implements Display {

	private final ListScorePanelViewer scorePanel;

	public ScoreViewViewer(ListScorePanelViewer scorePanel) {
		this.scorePanel = scorePanel;
	}


	@Override
	public void changeScoreList(List<ScoreTableRow> content) {
		scorePanel.changeScoreList(content);
	}

}
