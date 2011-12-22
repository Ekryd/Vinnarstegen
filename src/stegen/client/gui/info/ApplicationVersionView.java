package stegen.client.gui.info;

import stegen.client.gui.*;
import stegen.client.presenter.ApplicationVersionPresenter.Display;

import com.google.gwt.user.client.ui.*;

public class ApplicationVersionView implements Display {

	@Override
	public void setApplicationVersion(String applicationVersion) {
		BaseHtmlPage.VERSION_AREA.clearPanel();
		BaseHtmlPage.VERSION_AREA.addToPanel(new Label(applicationVersion));
	}

}
