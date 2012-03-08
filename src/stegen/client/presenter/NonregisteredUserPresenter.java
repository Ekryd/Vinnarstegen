package stegen.client.presenter;

import stegen.client.gui.*;
import stegen.shared.*;

public class NonregisteredUserPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final Shell shell;

	public interface Display {
		void setUserName(String name);
		void setShell(Shell shell);
	}

	public NonregisteredUserPresenter(Display loginButNotRegisteredView, LoginDataDto loginData,Shell shell) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
		this.shell = shell;
	}

	@Override
	public void go() {
		view.setUserName(loginData.player.nickname);
		view.setShell(shell);
	}

}
