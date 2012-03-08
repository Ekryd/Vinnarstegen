package stegen.client.presenter;

import stegen.client.gui.*;
import stegen.shared.*;

public class LogoutPresenter implements Presenter {

	private final LoginDataDto loginData;
	private final Display view;
	private final Shell shell;

	public interface Display {
		void setLogoutUrl(String logoutUrl);
		void setShell(Shell shell);
	}

	public LogoutPresenter(Display view, LoginDataDto loginData,Shell shell) {
		this.view = view;
		this.loginData = loginData;
		this.shell = shell;
	}

	@Override
	public void go() {
		view.setLogoutUrl(loginData.logoutUrl);
		view.setShell(shell);
	}
}
