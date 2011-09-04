package stegen.client.presenter;

import stegen.shared.*;

public class LogoutPresenter implements Presenter {

	private final LoginDataDto loginData;
	private final Display view;

	public interface Display {
		void setLogoutUrl(String logoutUrl);
	}

	public LogoutPresenter(Display view, LoginDataDto loginData) {
		this.view = view;
		this.loginData = loginData;
	}

	@Override
	public void go() {
		view.setLogoutUrl(loginData.logoutUrl);
	}

}
