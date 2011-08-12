package stegen.client.gui;

import stegen.client.gui.playeraction.*;
import stegen.client.gui.score.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.*;

public class MainContentTabs extends TabLayoutPanel {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;

	public MainContentTabs(MessageCentral messageCentral, LoginDataDto loginData) {
		super(2.5, Unit.EM);
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
	}

	private void init() {
		setAnimationDuration(1000);
		add(new ScrollPanel(new ListScorePanel(messageCentral, loginData)), "Poängställning");
		add(new GameResultPanel(messageCentral, loginData), "Matcher");
		add(new LoginStatusTable(messageCentral), "Inloggning");
		add(new PlayerMiscCommandTable(messageCentral), "Övrigt");
		setHeight("400px");
		selectTab(0);
	}

}
