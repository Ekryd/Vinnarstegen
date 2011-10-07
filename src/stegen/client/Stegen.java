package stegen.client;

import stegen.client.service.*;

import com.google.gwt.core.client.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Björn Ekryd
 */
public class Stegen implements EntryPoint {
	private PlayerCommandServiceAsync playerCommandService;
	private ScoreServiceAsync scoreService;
	private PlayerServiceAsync playerService;
	private AppController appController;

	/** This is the entry point method. */
	@Override
	public void onModuleLoad() {
		initServices();
		initApplicationController();
		startApplicationController();
	}

	private void initServices() {
		playerCommandService = GWT.create(PlayerCommandService.class);
		scoreService = GWT.create(ScoreService.class);
		playerService = GWT.create(PlayerService.class);
	}

	private void initApplicationController() {
		appController = new AppController(playerCommandService, scoreService, playerService);
	}

	private void startApplicationController() {
		appController.start(GWT.getHostPageBaseURL());
	}

}
