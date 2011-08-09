package stegen.client.presenter;

import stegen.shared.*;

public class NotLoggedInPresenter implements Presenter {

	private final Display loginView;
	private final LoginDataDto result;

	public interface Display {
		void setSignInUrl(String signInUrl);
	}

	public NotLoggedInPresenter(Display loginView, LoginDataDto result) {
		this.loginView = loginView;
		this.result = result;
	}

	@Override
	public void go() {
		loginView.setSignInUrl(result.signInUrl);
	}
}
