package stegen.client.gui;

import stegen.client.dto.*;
import stegen.client.gui.playeraction.*;
import stegen.client.gui.score.*;
import stegen.client.messages.*;

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
		add(new ListScorePanel(messageCentral, loginData), "Poängställning");
		add(new UndoPanel(messageCentral, loginData), "Händelser");
		setHeight("700px");
		selectTab(0);
	}

}
