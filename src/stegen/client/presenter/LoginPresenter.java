package stegen.client.presenter;

import stegen.shared.*;

public class LoginPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;

	public interface Display {
		void setSignInUrl(String signInUrl);
	}

	public LoginPresenter(Display loginView, LoginDataDto result) {
		this.view = loginView;
		this.result = result;
	}

	@Override
	public void go() {
		view.setSignInUrl(result.signInUrl);
	}
}
