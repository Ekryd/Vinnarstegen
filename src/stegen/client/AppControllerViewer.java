package stegen.client;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.gui.info.*;
import stegen.client.presenter.*;
import stegen.client.service.*;

public class AppControllerViewer {

	final EventBus eventBus;

	private AppControllerViewer(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public AppControllerViewer(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		this(EventBusImpl.create(playerCommandService, scoreService, playerService));
	}

	public static AppControllerViewer createForTest(EventBus eventBus) {
		return new AppControllerViewer(eventBus);
	}

	public void start() {
		new ApplicationVersionPresenter(new ApplicationVersionView(), eventBus).go();
		new CompositeMainPresenterViewer(new CompositeMainViewViewer(), eventBus).go();
		
	}
}
