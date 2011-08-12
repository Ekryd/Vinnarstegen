package stegen.client.presenter;

import stegen.shared.*;

public class NonregisteredUserPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;

	public interface Display {
		void setUserName(String name);
	}

	public NonregisteredUserPresenter(Display loginButNotRegisteredView, LoginDataDto result) {
		this.view = loginButNotRegisteredView;
		this.result = result;
	}

	@Override
	public void go() {
		view.setUserName(result.player.nickname);
	}

}
