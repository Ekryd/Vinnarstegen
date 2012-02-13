package stegen.client;

import stegen.client.service.*;

import com.google.gwt.core.client.*;

/**
 * @author Askia Linder
 */
public class Viewer implements EntryPoint {
	private PlayerCommandServiceAsync playerCommandService;
	private ScoreServiceAsync scoreService;
	private PlayerServiceAsync playerService;
	private AppControllerViewer appController;

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
		appController = new AppControllerViewer(playerCommandService, scoreService, playerService);
	}

	private void startApplicationController() {
		appController.start();
	}

}
