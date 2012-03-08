package stegen.client.gui.info;

import stegen.client.gui.*;
import stegen.client.presenter.ApplicationVersionPresenter.Display;

import com.google.gwt.user.client.ui.*;

public class ApplicationVersionView implements Display {
	private Label appVersion = new Label();
	
	@Override
	public void setApplicationVersion(String applicationVersion) {
		appVersion.setText(applicationVersion);
	}

	@Override
	public void setShell(Shell shell) {
		shell.showInVersionArea(appVersion);
	}
}
