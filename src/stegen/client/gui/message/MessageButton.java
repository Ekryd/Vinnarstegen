package stegen.client.gui.message;

import java.util.*;

import stegen.client.messages.*;
import stegen.client.service.*;
import stegen.client.service.messageprefix.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class MessageButton extends Button implements PlayerCommandListener {
	private final MessageCentral messageCentral;
	private MessagePrefix buttonText;
	private final LoginDataDto loginData;
	private final MessagePrefixGenerator generator = new MessagePrefixGeneratorImpl();

	public MessageButton(MessageCentral messageCentral, final LoginDataDto loginData) {
		super("");
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
		addHandler();
		messageCentral.listeners.addListener(this);
	}

	private void init() {
		setStylePrimaryName("button");
	}

	private void addHandler() {
		addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				MessageDialog ropaDialog = new MessageDialog(messageCentral, loginData, buttonText);
				int left = getAbsoluteLeft() + 10;
				int top = getAbsoluteTop() + 10;
				ropaDialog.setPopupPosition(left, top);

				ropaDialog.show();
			}
		});
	}

	private void changeButtonText() {
		buttonText = generator.getRandomizedPrefix();
		setText(buttonText.buttonText);
	}

	@Override
	public void onPlayerMiscCommandListUpdate(List<PlayerCommandDto> result) {
		changeButtonText();
	}

	@Override
	public void onUndoCommand(UndoPlayerCommandResult result) {}

	@Override
	public void onUndoCommandUpdate(PlayerCommandDto result) {}

	@Override
	public void onGameResultListUpdate(List<PlayerCommandDto> result) {}

	@Override
	public void onLoginStatusListUpdate(List<PlayerCommandDto> result) {}

}
