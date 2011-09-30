package stegen.client.gui.player;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.NonregisteredUserPresenter.Display;

public class NonregisteredUserView implements Display {

	private final UserPanel userPanel = new UserPanel();

	public NonregisteredUserView() {
		USER_AREA.clearPanel();
		USER_AREA.addToPanel(userPanel);
	}

	@Override
	public void setUserName(String name) {
		userPanel.setPlayerName(name);
	}

}
