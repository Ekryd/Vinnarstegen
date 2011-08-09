package stegen.client.gui.player;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.client.ui.*;

public class UserPanel extends HorizontalPanel implements NicknameListener {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private Label userLabel;

	public UserPanel(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		messageCentral.listeners.addListener(this);
		init();
	}

	private void init() {
		userLabel = new Label("Välkommen " + loginData.player.nickname + " ");
		add(userLabel);
		setCellVerticalAlignment(userLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		add(new ChangeNicknameButton(messageCentral, loginData));
	}

	@Override
	public void onNicknameChange(String nickname) {
		userLabel.setText("Välkommen " + nickname + " ");

	}
}
