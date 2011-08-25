package stegen.client.gui;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.gui.challenge.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.CompositeMainPresenter.Display;

public class CompositeMainView implements Display {
	private final MainContentTable mainContentTable = new MainContentTable();
	private final ScoreView scoreView;
	private final ChallengeView challengeView;

	public CompositeMainView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(mainContentTable);
		ListScorePanel2 scorePanel = mainContentTable.getScorePanel();
		scoreView = new ScoreView(scorePanel);
		challengeView = new ChallengeView(scorePanel.getChallengeButtonColumn(), scorePanel.getChallengeDialog());
	}

	@Override
	public stegen.client.presenter.ScorePresenter.Display getScoreView() {
		return scoreView;
	}

	@Override
	public stegen.client.presenter.ChallengePresenter.Display getChallengeView() {
		return challengeView;
	}

}
