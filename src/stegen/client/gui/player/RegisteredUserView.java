package stegen.client.gui.player;
import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.RegisteredUserPresenter.Display;

import com.google.gwt.event.dom.client.*;

public class RegisteredUserView implements Display {

	private final UserPanelWithChangeNicknameButton userPanel = new UserPanelWithChangeNicknameButton();

	public RegisteredUserView() {
		USER_AREA.clearPanel();
		USER_AREA.addToPanel(userPanel);
	}

	@Override
	public void setUserName(String name) {
		userPanel.setPlayerName(name);
	}

	@Override
	public String getNewNickname() {
		return userPanel.getNewNickname();
	}

	@Override
	public void addClickChangeUserNameHandler(ClickHandler clickHandler) {
		userPanel.addClickChangeUserNameHandler(clickHandler);
	}

}
