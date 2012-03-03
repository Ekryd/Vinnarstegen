package stegen.client.gui;

import stegen.client.*;
import stegen.client.gui.desktop.*;

import com.google.gwt.user.client.ui.*;
@Deprecated
public enum BaseHtmlPage {
	MAIN_AREA(ShellDesktop.mainArea),
	LOGOUT_AREA(ShellDesktop.logoutArea),
	USER_AREA(ShellDesktop.userArea),
	REFRESH_AREA(ShellDesktop.refreshArea),
	VERSION_AREA(ShellDesktop.versionArea);

	protected FlowPanel areaName;

	private BaseHtmlPage(FlowPanel areaName) {
		this.areaName = areaName;
	}
	
	public void clearPanel() {
		areaName.clear();
	}

	public void addToPanel(IsWidget widget) {
		areaName.add(widget);
	}

}
