package stegen.client.gui;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.user.client.ui.*;

public class UserPanel extends HorizontalPanel {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;

	public UserPanel(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
	}

	private void init() {
		add(new Label("Välkommen " + loginData.nickname));
	}

}
