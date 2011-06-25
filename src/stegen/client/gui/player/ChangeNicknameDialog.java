package stegen.client.gui.player;

import stegen.client.dto.*;
import stegen.client.gui.dialog.*;
import stegen.client.messages.*;

public class ChangeNicknameDialog extends InputDialogBox implements NicknameListener {

	private String oldNickname;

	public ChangeNicknameDialog(MessageCentral messageCentral, LoginDataDto loginData) {
		super(messageCentral, loginData);
		this.oldNickname = loginData.player.nickname;
		messageCentral.listeners.addListener(this);
	}

	@Override
	protected void onOkButtonClick(MessageCentral messageCentral, LoginDataDto loginData, String messageBoxText) {
		messageCentral.changeNickname(loginData.player, messageBoxText);
	}

	@Override
	protected String getMessageLabelText() {
		return "Ändra från " + oldNickname + " till";
	}

	@Override
	public void onNicknameChange(String nickname) {
		oldNickname = nickname;
	}

}
