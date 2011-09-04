package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.GameResultsPresenter.Display;

public class GameResultsView implements Display {

	private final GameResultPanel2 gameResultPanel;

	public GameResultsView(GameResultPanel2 gameResultPanel) {
		this.gameResultPanel = gameResultPanel;
	}

	@Override
	public void changeGameResultList(List<GameResultsRow> content) {
		gameResultPanel.changeGameResultList(content);
	}

}
