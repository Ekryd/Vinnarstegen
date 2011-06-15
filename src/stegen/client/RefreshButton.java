package stegen.client;

import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class RefreshButton extends Button {
	private final MessageCentral messageCentral;

	public RefreshButton(MessageCentral messageCentral) {
		super("Refresh!");
		this.messageCentral = messageCentral;
		initHandler();
	}

	private void initHandler() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				messageCentral.updateAll();
			}
		});
	}
}
