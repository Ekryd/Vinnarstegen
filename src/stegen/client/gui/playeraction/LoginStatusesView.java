package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.presenter.LoginStatusesPresenter.Display;

public class LoginStatusesView implements Display {

	private final LoginStatusTable2 loginStatusTable;

	public LoginStatusesView(LoginStatusTable2 loginStatusTable) {
		this.loginStatusTable = loginStatusTable;
	}

	@Override
	public void changeLoginStatusList(List<LoginStatusRow> content) {
		loginStatusTable.changeList(content);
	}

}
