package stegen.client.gui.message;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.client.ui.*;

public class MessageArea extends VerticalPanel {
	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;

	public MessageArea(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
	}

	private void init() {
		add(new MessageButton(messageCentral, loginData));
		add(new MessageTable(messageCentral));
	}

}
