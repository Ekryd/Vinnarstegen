package stegen.client.gui;

import stegen.client.gui.message.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.rules.*;
import stegen.client.gui.score.*;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class MainContentTable implements IsWidget {

	private static final double BAR_HEIGHT = 2.5;
	private static final Unit BAR_UNIT = Unit.EM;
	private final TabLayoutPanel baseWidget = new TabLayoutPanel(BAR_HEIGHT, BAR_UNIT);
	private final ListScorePanel scorePanel = new ListScorePanel();
	private final GameResultPanel gameResultPanel = new GameResultPanel();
	private final LoginStatusTable loginStatusTable = new LoginStatusTable();
	private final PlayerMiscCommandTable playerMiscCommandTable = new PlayerMiscCommandTable();
	private final MessagePanel messagePanel = new MessagePanel();
	private final RulesPanel rulesPanel = new RulesPanel();

	public MainContentTable() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setAnimationDuration(1000);
		baseWidget.add(scorePanel, "Poängställning");
		baseWidget.add(messagePanel, "Klotterplank");
		baseWidget.add(gameResultPanel, "Matcher");
		baseWidget.add(wrapInVerticalPanel(loginStatusTable), "Inloggning");
		baseWidget.add(wrapInVerticalPanel(playerMiscCommandTable), "Övrigt");
		baseWidget.add(rulesPanel, "Regler");
		baseWidget.setHeight("1000px");
		baseWidget.selectTab(0);
	}

	/**
	 * This is donw to prevent ugly GUI layout in FireFox. The rows height where
	 * expanded to fill the whole page
	 */
	private VerticalPanel wrapInVerticalPanel(IsWidget table) {
		VerticalPanel panel = new VerticalPanel();
		panel.add(table);
		return panel;
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public ListScorePanel getScorePanel() {
		return scorePanel;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}

	public GameResultPanel getGameResultPanel() {
		return gameResultPanel;
	}

	public LoginStatusTable getLoginStatusesPanel() {
		return loginStatusTable;
	}

	public PlayerMiscCommandTable getPlayerMiscCommandsPanel() {
		return playerMiscCommandTable;
	}

	public RulesPanel getRulesPanel() {
		return rulesPanel;
	}

}
