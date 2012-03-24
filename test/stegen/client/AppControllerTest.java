package stegen.client;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.gui.*;
import stegen.client.service.*;

import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

/**
 * Tyvärr kan vi inte testa eventkoden eftersom den är beroende av konkreta
 * GUI-klasser för presenters
 */
@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class AppControllerTest {

	private AppController controller;
	@Mock
	private Shell shell;
	@Mock
	private HasWidgets.ForIsWidget parentView;
	@Mock
	private PlayerCommandServiceAsync playerCommandService;
	@Mock
	private ScoreServiceAsync scoreService;
	@Mock
	private PlayerServiceAsync playerService;
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;

	@Test
	public void testInitializeEventBus() {
		controller = new AppController(playerCommandService, scoreService, playerService,shell,parentView,gwtEventBus);
		assertNotNull(controller.shell);
		assertNotNull(controller.parentView);
		assertNotNull(controller.gwtEventBus);
	}

	@Test
	public void testInitialize() {
		controller = new AppController(playerCommandService, scoreService, playerService,shell,parentView,gwtEventBus);

		controller.start("hostPageBaseURL");
		
		verify(playerService).getUserLoginStatus(eq("hostPageBaseURL"),any(AsyncCallback.class));
	}

}
