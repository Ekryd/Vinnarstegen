package stegen.client.gui;

import stegen.client.gui.message.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.rules.*;
import stegen.client.gui.score.*;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class MainContentTableViewer implements IsWidget {

	private static final double BAR_HEIGHT = 2.5;
	private static final Unit BAR_UNIT = Unit.EM;
	private final TabLayoutPanel baseWidget = new TabLayoutPanel(BAR_HEIGHT, BAR_UNIT);
	private final ListScorePanelViewer scorePanel = new ListScorePanelViewer();
	private final GameResultPanel gameResultPanel = new GameResultPanel();
	private final MessagePanel messagePanel = new MessagePanel();
	private final RulesPanel rulesPanel = new RulesPanel();

	public MainContentTableViewer() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setAnimationDuration(1000);
		baseWidget.add(scorePanel, "Poängställning");
		baseWidget.add(messagePanel, "Meddelanden");
		baseWidget.add(gameResultPanel, "Matcher");
		baseWidget.add(rulesPanel, "Regler");
		baseWidget.setHeight("1000px");
		baseWidget.selectTab(0);
	}


	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public ListScorePanelViewer getScorePanel() {
		return scorePanel;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}

	public GameResultPanel getGameResultPanel() {
		return gameResultPanel;
	}

	public RulesPanel getRulesPanel() {
		return rulesPanel;
	}

}
