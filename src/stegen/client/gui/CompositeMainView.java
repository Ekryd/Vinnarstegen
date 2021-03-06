package stegen.client.gui;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.gui.challenge.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.message.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.CompositeMainPresenter.Display;

public class CompositeMainView implements Display {
	private final MainContentTable mainContentTable = new MainContentTable();
	private final ScoreView scoreView;
	private final MessagesView messagesView;
	private final ChallengeInputView challengeView;
	private final WinGameInputView gameInputView;
	private final GameResultsView gameResultsView;
	private final UndoView undoView;
	private final LoginStatusesView loginStatusesView;
	private final PlayerMiscCommandsView playerMiscCommandView;

	public CompositeMainView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(mainContentTable);
		ListScorePanel scorePanel = mainContentTable.getScorePanel();
		GameResultPanel gameResultPanel = mainContentTable.getGameResultPanel();
		scoreView = new ScoreView(scorePanel);
		messagesView = new MessagesView(mainContentTable.getMessagePanel());
		challengeView = new ChallengeInputView(scorePanel.getChallengeButtonColumn(), scorePanel.getChallengeDialog());
		gameInputView = new WinGameInputView(scorePanel.getWinnerButtonColumn(), scorePanel.getLoserButtonColumn(),
				scorePanel.getWinGameDialog());
		gameResultsView = new GameResultsView(gameResultPanel);
		undoView = new UndoView(gameResultPanel.getUndoButton());
		loginStatusesView = new LoginStatusesView(mainContentTable.getLoginStatusesPanel());
		playerMiscCommandView = new PlayerMiscCommandsView(mainContentTable.getPlayerMiscCommandsPanel());
	}

	@Override
	public stegen.client.presenter.ScorePresenter.Display getScoreView() {
		return scoreView;
	}

	@Override
	public stegen.client.presenter.ChallengePresenter.Display getChallengeInputView() {
		return challengeView;
	}

	@Override
	public stegen.client.presenter.WinGameInputPresenter.Display getWinGameInputView() {
		return gameInputView;
	}

	@Override
	public stegen.client.presenter.GameResultsPresenter.Display getGameResultsView() {
		return gameResultsView;
	}

	@Override
	public stegen.client.presenter.UndoPresenter.Display getUndoView() {
		return undoView;
	}

	@Override
	public stegen.client.presenter.LoginStatusesPresenter.Display getLoginStatusesView() {
		return loginStatusesView;
	}

	@Override
	public stegen.client.presenter.PlayerMiscCommandsPresenter.Display getPlayerMiscCommandView() {
		return playerMiscCommandView;
	}

	@Override
	public stegen.client.presenter.MessagesPresenter.Display getMessageView() {
		return messagesView;
	}

}
