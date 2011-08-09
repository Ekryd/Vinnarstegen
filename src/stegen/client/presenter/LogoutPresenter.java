package stegen.client.presenter;

import stegen.shared.*;

public class LogoutPresenter implements Presenter {

	private final LoginDataDto result;
	private final Display view;

	public interface Display {
		void setLogoutUrl(String logoutUrl);
	}

	public LogoutPresenter(Display view, LoginDataDto result) {
		this.view = view;
		this.result = result;
	}

	@Override
	public void go() {
		view.setLogoutUrl(result.logoutUrl);
	}

}
