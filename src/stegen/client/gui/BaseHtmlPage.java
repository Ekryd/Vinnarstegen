package stegen.client.gui;

import stegen.client.UILayout;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public enum BaseHtmlPage {
	MAIN_AREA(UILayout.mainArea),
	LOGOUT_AREA(UILayout.logoutArea),
	USER_AREA(UILayout.userArea),
	REFRESH_AREA(UILayout.refreshArea),
	VERSION_AREA(UILayout.versionArea);

	private FlowPanel areaName;

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
