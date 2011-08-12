package stegen.client.gui.login;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.LoginPresenter.Display;

public class LoginView implements Display {

	private LoginPanel loginPanel = new LoginPanel();

	public LoginView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(loginPanel);
	}

	@Override
	public void setSignInUrl(String signInUrl) {
		loginPanel.setSignInUrl(signInUrl);
	}

}
