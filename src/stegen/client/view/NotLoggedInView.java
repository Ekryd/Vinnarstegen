package stegen.client.view;

import static stegen.client.view.BaseHtmlPage.*;
import stegen.client.gui.player.*;
import stegen.client.presenter.NotLoggedInPresenter.Display;

public class NotLoggedInView implements Display {

	private LoginPanel loginPanel = new LoginPanel();

	public NotLoggedInView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(loginPanel);
	}

	@Override
	public void setSignInUrl(String signInUrl) {
		loginPanel.setSignInUrl(signInUrl);
	}

}
