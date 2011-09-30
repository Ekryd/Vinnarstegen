package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.GameResultsPresenter.Display;

public class GameResultsView implements Display {

	private final GameResultPanel gameResultPanel;

	public GameResultsView(GameResultPanel gameResultPanel) {
		this.gameResultPanel = gameResultPanel;
	}

	@Override
	public void changeGameResultList(List<GameResultsRow> content) {
		gameResultPanel.changeGameResultList(content);
	}

}
