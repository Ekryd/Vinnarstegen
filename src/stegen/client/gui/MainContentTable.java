package stegen.client.gui;

import stegen.client.gui.score.*;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class MainContentTable implements IsWidget {

	private static final double BAR_HEIGHT = 2.5;
	private static final Unit BAR_UNIT = Unit.EM;
	private final TabLayoutPanel baseWidget = new TabLayoutPanel(BAR_HEIGHT, BAR_UNIT);
	private ListScorePanel2 scorePanel = new ListScorePanel2();

	public MainContentTable() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setAnimationDuration(1000);
		baseWidget.add(scorePanel, "Poängställning");
		// baseWidget.add(new GameResultPanel(messageCentral, loginData),
		// "Matcher");
		// baseWidget.add(new LoginStatusTable(messageCentral), "Inloggning");
		// baseWidget.add(new PlayerMiscCommandTable(messageCentral), "Övrigt");
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

}
