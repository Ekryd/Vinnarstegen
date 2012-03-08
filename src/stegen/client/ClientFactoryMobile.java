package stegen.client;

import stegen.client.gui.*;
import stegen.client.gui.mobile.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

class ClientFactoryMobile implements ClientFactory {

	private AppController appController;
	private Shell shell;

	public ClientFactoryMobile() {
//		RootLayoutPanel.get().add(getShell());
		appController = new AppController((PlayerCommandServiceAsync) GWT.create(PlayerCommandService.class), (ScoreServiceAsync) GWT.create(ScoreService.class),
				(PlayerServiceAsync) GWT.create(PlayerService.class), getShell(),RootLayoutPanel.get());
	}

	@Override
	public AppController getApp() {
		return appController;
	}

	@Override
	public Shell getShell() {
		if (shell == null) {
			shell = createShell();
		}
		return shell;
	}

	protected Shell createShell() {
		return new ShellMobile();
	}

}
