package stegen.client;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.client.ui.*;

/**
 * Tyvärr kan vi inte testa eventkoden eftersom den är beroende av konkreta
 * GUI-klasser för presenters
 */
@RunWith(MockitoJUnitRunner.class)
public class AppControllerTest {

	private AppController controller;
	@Mock
	private EventBus eventBus;
	@Mock
	private Shell shell;
	@Mock
	private HasWidgets.ForIsWidget parentView;

	@Test
	public void testInitializeEventBus() {
		PlayerCommandServiceAsync playerCommandService = Mockito.mock(PlayerCommandServiceAsync.class);
		ScoreServiceAsync scoreService = Mockito.mock(ScoreServiceAsync.class);
		PlayerServiceAsync playerService = Mockito.mock(PlayerServiceAsync.class);
		controller = new AppController(playerCommandService, scoreService, playerService,shell,parentView);
		assertNotNull(controller.eventBus);
		assertNotNull(controller.shell);
		assertNotNull(controller.parentView);
	}

	@Test
	public void testInitialize() {
		setupController();

		controller.start("hostPageBaseURL");
		
		InOrder inOrder = inOrder(eventBus,parentView);
		inOrder.verify(eventBus).addHandler(any(UpdateLoginStatusCallback.class));
		inOrder.verify(eventBus).getUserLoginStatus("hostPageBaseURL");
		inOrder.verify(parentView).add(shell);
	}

	private void setupController() {
		controller = AppController.createForTest(eventBus,shell,parentView);
	}
}
