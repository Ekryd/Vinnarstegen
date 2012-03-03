package stegen.client.presenter;

import stegen.client.gui.*;
import stegen.shared.*;

public class LoginPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;

	public interface Display {
		void setSignInUrl(String signInUrl);
		void setShell(Shell shell);
	}

	public LoginPresenter(Display loginView, LoginDataDto loginData) {
		this.view = loginView;
		this.loginData = loginData;
	}

	@Override
	public void go() {
		view.setSignInUrl(loginData.signInUrl);
	}
}
