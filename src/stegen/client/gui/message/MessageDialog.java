package stegen.client.gui.message;

import stegen.client.gui.dialog.*;
import stegen.client.messages.*;
import stegen.client.service.messageprefix.*;
import stegen.shared.*;

public class MessageDialog extends InputDialogBox {
	private final String messageLabelText;

	public MessageDialog(MessageCentral messageCentral, LoginDataDto loginData, MessagePrefix buttonText) {
		super(messageCentral, loginData);
		this.messageLabelText = loginData.player.nickname + " " + buttonText.actionText;
	}

	@Override
	protected String getMessageLabelText() {
		return messageLabelText;
	}

	@Override
	protected void onDialogOkButtonClick(MessageCentral messageCentral, LoginDataDto loginData, String messageBoxText) {
		messageCentral.sendMessage(loginData.player, messageLabelText + " " + messageBoxText);
	}
}
