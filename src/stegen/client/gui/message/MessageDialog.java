package stegen.client.gui.message;

import stegen.client.dto.*;
import stegen.client.gui.dialog.*;
import stegen.client.messages.*;

public class MessageDialog extends InputDialogBox {
	private final String messageLabelText;

	public MessageDialog(MessageCentral messageCentral, LoginDataDto loginData, ButtonText buttonText) {
		super(messageCentral, loginData);
		this.messageLabelText = loginData.player.nickname + " " + buttonText.actionText;
	}

	@Override
	protected String getMessageLabelText() {
		return messageLabelText;
	}

	@Override
	protected void onOkButtonClick(MessageCentral messageCentral, LoginDataDto loginData, String messageBoxText) {
		messageCentral.sendMessage(loginData.player, messageLabelText + " " + messageBoxText);
	}
}
