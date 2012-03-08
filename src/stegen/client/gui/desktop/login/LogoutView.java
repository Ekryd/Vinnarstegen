package stegen.client.gui.desktop.login;

import stegen.client.gui.*;
import stegen.client.presenter.LogoutPresenter.Display;

import com.google.gwt.user.client.ui.*;

public class LogoutView implements Display {

	private final Anchor logoutLink = new Anchor("Logga ut");

	public LogoutView() {
	}

	@Override
	public void setLogoutUrl(String logoutUrl) {
		logoutLink.setHref(logoutUrl);
	}

	@Override
	public void setShell(Shell shell) {
		shell.showInLogoutArea(logoutLink);
	}
}
