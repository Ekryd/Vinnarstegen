package stegen.client.gui;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.gui.score.*;
import stegen.client.presenter.CompositeMainPresenter.Display;

public class CompositeMainView implements Display {
	private final MainContentTable mainContentTable = new MainContentTable();
	private final ScoreView scoreView;

	public CompositeMainView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(mainContentTable);
		scoreView = new ScoreView(mainContentTable.getScorePanel());
	}

	@Override
	public stegen.client.presenter.ScorePresenter.Display getScoreView() {
		return scoreView;
	}

}
