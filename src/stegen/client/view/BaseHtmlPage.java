package stegen.client.view;

import com.google.gwt.user.client.ui.*;

public enum BaseHtmlPage {
	MAIN_AREA("mainArea"),
	LOGOUT_AREA("logoutArea"),
	USER_AREA("userArea"),
	MESSAGES_AREA("messagesArea"),
	REFRESH_AREA("refreshArea");

	private String areaName;

	private BaseHtmlPage(String areaName) {
		this.areaName = areaName;
	}

	public void clearPanel() {
		RootPanel.get(areaName).clear();
	}

	public void addToPanel(IsWidget widget) {
		RootPanel.get(areaName).add(widget);
	}

}
