package stegen.client;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.client.ui.*;

/**
 * Tyvärr kan vi inte testa eventkoden eftersom den är beroende av konkreta
 * GUI-klasser för presenters
 */
public class AppControllerTest {

	private AppController controller;
	private EventBus eventBus;
	private Shell shell;

	@Test
	public void testInitializeEventBus() {
		PlayerCommandServiceAsync playerCommandService = createStrictMock(PlayerCommandServiceAsync.class);
		ScoreServiceAsync scoreService = createStrictMock(ScoreServiceAsync.class);
		PlayerServiceAsync playerService = createStrictMock(PlayerServiceAsync.class);
		shell = createStrictMock(Shell.class);
		controller = new AppController(playerCommandService, scoreService, playerService,shell);
		assertNotNull(controller.eventBus);
	}

	@Test
	public void testInitialize() {
		setupController();

		setupInitializationExpects();
		controller.start("hostPageBaseURL");

		verify(eventBus);
	}

	private void setupController() {
		eventBus = createStrictMock(EventBus.class);
		controller = AppController.createForTest(eventBus);
	}

	private void setupInitializationExpects() {
		eventBus.addHandler(controller.eventCheckLoginStatusHandler);
		eventBus.getUserLoginStatus("hostPageBaseURL");
		replay(eventBus);
	}

}
