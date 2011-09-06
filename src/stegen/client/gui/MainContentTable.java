package stegen.client.gui;

import stegen.client.gui.playeraction.*;
import stegen.client.gui.score.*;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class MainContentTable implements IsWidget {

	private static final double BAR_HEIGHT = 2.5;
	private static final Unit BAR_UNIT = Unit.EM;
	private final TabLayoutPanel baseWidget = new TabLayoutPanel(BAR_HEIGHT, BAR_UNIT);
	private final ListScorePanel2 scorePanel = new ListScorePanel2();
	private final GameResultPanel2 gameResultPanel = new GameResultPanel2();
	private final LoginStatusTable2 loginStatusTable = new LoginStatusTable2();
	private final PlayerMiscCommandTable2 playerMiscCommandTable = new PlayerMiscCommandTable2();

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

	public ListScorePanel2 getScorePanel() {
		return scorePanel;
	}

	public GameResultPanel2 getGameResultPanel() {
		return gameResultPanel;
	}

	public LoginStatusTable2 getLoginStatusesPanel() {
		return loginStatusTable;
	}

	public PlayerMiscCommandTable2 getPlayerMiscCommandsPanel() {
		return playerMiscCommandTable;
	}

}
