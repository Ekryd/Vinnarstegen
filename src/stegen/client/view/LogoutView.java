package stegen.client.view;

import static stegen.client.view.BaseHtmlPage.*;
import stegen.client.presenter.LogoutPresenter.Display;

import com.google.gwt.user.client.ui.*;

public class LogoutView implements Display {

	private final Anchor logoutLink = new Anchor("Logga ut");

	public LogoutView() {
		LOGOUT_AREA.clearPanel();
		LOGOUT_AREA.addToPanel(logoutLink);
	}

	@Override
	public void setLogoutUrl(String logoutUrl) {
		logoutLink.setHref(logoutUrl);
	}
}
