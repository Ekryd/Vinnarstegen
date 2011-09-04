package stegen.client.gui;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.gui.challenge.*;
import stegen.client.gui.gameresult.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.CompositeMainPresenter.Display;

public class CompositeMainView implements Display {
	private final MainContentTable mainContentTable = new MainContentTable();
	private final ScoreView scoreView;
	private final ChallengeInputView challengeView;
	private final WinGameInputView gameInputView;
	private final GameResultsView gameResultsView;
	private final UndoView undoView;

	public CompositeMainView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(mainContentTable);
		ListScorePanel2 scorePanel = mainContentTable.getScorePanel();
		GameResultPanel2 gameResultPanel = mainContentTable.getGameResultPanel();
		scoreView = new ScoreView(scorePanel);
		challengeView = new ChallengeInputView(scorePanel.getChallengeButtonColumn(), scorePanel.getChallengeDialog());
		gameInputView = new WinGameInputView(scorePanel.getWinnerButtonColumn(), scorePanel.getLoserButtonColumn(),
				scorePanel.getWinGameDialog());
		gameResultsView = new GameResultsView(gameResultPanel);
		undoView = new UndoView(gameResultPanel.getUndoButton());
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

}
