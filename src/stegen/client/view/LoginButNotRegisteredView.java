package stegen.client.view;

import static stegen.client.view.BaseHtmlPage.*;
import stegen.client.gui.player.*;
import stegen.client.gui.register.*;
import stegen.client.presenter.LoginButNotRegisteredPresenter.Display;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class LoginButNotRegisteredView implements Display {

	private final Anchor logoutLink = new Anchor("Logga ut");
	private final UserPanel2 userPanel = new UserPanel2();
	private final RegisterPanel2 registerPanel = new RegisterPanel2();

	public LoginButNotRegisteredView() {
		LOGOUT_AREA.clearPanel();
		LOGOUT_AREA.addToPanel(logoutLink);
		USER_AREA.clearPanel();
		USER_AREA.addToPanel(userPanel);
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(registerPanel);
	}

	@Override
	public void setLogoutUrl(String logoutUrl) {
		logoutLink.setHref(logoutUrl);
	}

	@Override
	public void setUserName(String name) {
		userPanel.setPlayerName(name);
	}

	@Override
	public void addClickRegistrationHandler(ClickHandler clickHandler) {
		registerPanel.addClickRegistrationHandler(clickHandler);
	}

	@Override
	public String getRegistrationCode() {
		return registerPanel.getRegistrationText();
	}

	@Override
	public void showRegistrationFail() {
		registerPanel.showRegistrationFail();
	}
}
