package stegen.client.gui;

import stegen.client.gui.playeraction.*;
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

	public MainContentTable() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setAnimationDuration(1000);
		baseWidget.add(scorePanel, "Poängställning");
		baseWidget.add(gameResultPanel, "Matcher");
		baseWidget.add(loginStatusTable, "Inloggning");
		baseWidget.add(playerMiscCommandTable, "Övrigt");
		baseWidget.setHeight("400px");
		baseWidget.selectTab(0);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public ListScorePanel getScorePanel() {
		return scorePanel;
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

}
