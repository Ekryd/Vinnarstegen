package stegen.client.gui;

import stegen.client.gui.message.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.CompositeMainPresenterViewer.Display;

import com.google.gwt.user.client.ui.*;

public class CompositeMainViewViewer implements Display {
	private final MainContentTableViewer mainContentTable = new MainContentTableViewer();
	private final ScoreViewViewer scoreView;
	private final MessagesViewViewer messagesView;
	private final GameResultsViewViewer gameResultsView;
	
	public CompositeMainViewViewer() {
		RootPanel.get("viewerArea").add(mainContentTable);
		ListScorePanelViewer scorePanel = mainContentTable.getScorePanel();
		GameResultPanel gameResultPanel = mainContentTable.getGameResultPanel();
		scoreView = new ScoreViewViewer(scorePanel);
		messagesView = new MessagesViewViewer(mainContentTable.getMessagePanel());
		gameResultsView = new GameResultsViewViewer(gameResultPanel);
	}

	@Override
	public stegen.client.presenter.ScorePresenterViewer.Display getScoreView() {
		return scoreView;
	}

	@Override
	public stegen.client.presenter.GameResultsPresenterViewer.Display getGameResultsView() {
		return gameResultsView;
	}

	@Override
	public stegen.client.presenter.MessagesPresenterViewer.Display getMessageView() {
		return messagesView;
	}

}
