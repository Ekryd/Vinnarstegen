package stegen.client.gui.score;

import java.util.*;

import stegen.client.presenter.ScorePresenter.Display;

import com.google.gwt.event.dom.client.*;

public class ScoreView implements Display {

	private final ListScorePanel2 scorePanel;

	public ScoreView(ListScorePanel2 scorePanel) {
		this.scorePanel = scorePanel;
	}

	@Override
	public void addCleanScoresHandler(ClickHandler clickHandler) {
		scorePanel.addCleanScoresHandler(clickHandler);
	}

	@Override
	public void changeScoreList(List<ScoreTableRow> content) {
		scorePanel.changeScoreList(content);
	}

}
