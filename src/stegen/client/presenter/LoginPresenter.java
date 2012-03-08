package stegen.client.presenter;

import stegen.client.gui.*;
import stegen.shared.*;

public class LoginPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final Shell shell;

	public interface Display {
		void setSignInUrl(String signInUrl);
		void setShell(Shell shell);
	}

	public LoginPresenter(Display loginView, LoginDataDto loginData, Shell shell) {
		this.view = loginView;
		this.loginData = loginData;
		this.shell = shell;
	}

	@Override
	public void go() {
		view.setSignInUrl(loginData.signInUrl);
		view.setShell(shell);
	}
}
