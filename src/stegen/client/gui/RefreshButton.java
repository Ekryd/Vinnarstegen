package stegen.client.gui;

import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class RefreshButton extends Button {
	private final MessageCentral messageCentral;

	public RefreshButton(MessageCentral messageCentral) {
		super("Refresh!");
		this.messageCentral = messageCentral;
		inti();
		initHandler();
	}

	private void inti() {
		setStylePrimaryName("thinButton");
	}

	private void initHandler() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				messageCentral.updateScoreAndCommands();
			}
		});
	}
}
