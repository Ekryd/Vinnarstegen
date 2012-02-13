package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.GameResultsPresenterViewer.Display;

public class GameResultsViewViewer implements Display {

	private final GameResultPanel gameResultPanel;

	public GameResultsViewViewer(GameResultPanel gameResultPanel) {
		this.gameResultPanel = gameResultPanel;
	}

	@Override
	public void changeGameResultList(List<GameResultsRow> content) {
		gameResultPanel.changeGameResultList(content);
	}

}
