package stegen.client;

import stegen.client.gui.*;
import stegen.client.gui.desktop.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.event.shared.*;
import com.google.gwt.user.client.ui.*;

class ClientFactoryDesktop implements ClientFactory {

	private Shell shell;

	private final EventBus eventBus = new SimpleEventBus();

	public ClientFactoryDesktop() {
	}

	@Override
	public AppController getApp() {
		return new AppController((PlayerCommandServiceAsync) GWT.create(PlayerCommandService.class), (ScoreServiceAsync) GWT.create(ScoreService.class),
				(PlayerServiceAsync) GWT.create(PlayerService.class), getShell(),RootLayoutPanel.get(),getEventBus());
	}
	
	
	
	@Override
	public Shell getShell() {
		if( shell == null){
			shell = createShell();
		}
		return shell;
	}

	protected Shell createShell() {
		return new ShellDesktop();
	 }

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}
}
