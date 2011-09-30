package stegen.client.gui.player;

import com.google.gwt.event.dom.client.*;

public class UserPanelWithChangeNicknameButton extends UserPanel {

	private final ChangeNicknameButton changeNicknameButton = new ChangeNicknameButton();

	public UserPanelWithChangeNicknameButton() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.add(changeNicknameButton);
	}

	@Override
	public void setPlayerName(String name) {
		super.setPlayerName(name);
		changeNicknameButton.setPlayerName(name);
	}

	public String getNewNickname() {
		return changeNicknameButton.getNewNickname();
	}

	public void addClickChangeUserNameHandler(ClickHandler clickHandler) {
		changeNicknameButton.addClickChangeUserNicknameHandler(clickHandler);
	}

}
