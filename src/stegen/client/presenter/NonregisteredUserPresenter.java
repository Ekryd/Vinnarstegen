package stegen.client.presenter;

import stegen.shared.*;

public class NonregisteredUserPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;

	public interface Display {
		void setUserName(String name);
	}

	public NonregisteredUserPresenter(Display loginButNotRegisteredView, LoginDataDto loginData) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
	}

	@Override
	public void go() {
		view.setUserName(loginData.player.nickname);
	}

}
