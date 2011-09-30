package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.LoginStatusesPresenter.Display;

public class LoginStatusesView implements Display {

	private final LoginStatusTable loginStatusTable;

	public LoginStatusesView(LoginStatusTable loginStatusTable) {
		this.loginStatusTable = loginStatusTable;
	}

	@Override
	public void changeLoginStatusList(List<LoginStatusRow> content) {
		loginStatusTable.changeList(content);
	}

}
