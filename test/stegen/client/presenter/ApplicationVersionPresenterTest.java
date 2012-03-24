package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.gui.*;
import stegen.client.presenter.ApplicationVersionPresenter.Display;
import stegen.client.service.*;
@RunWith(MockitoJUnitRunner.class)
public class ApplicationVersionPresenterTest {

	@Mock
	private Display view;
	@Mock
	private PlayerServiceAsync playerService;
	@Mock
	private Shell shell;
	
	private ApplicationVersionPresenter presenter;

	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	@Test
	public void testSetVersion() {
		setupPresenter();
	
		presenter.go();

		presenter.eventUpdateApplicationVersion.onSuccess("42");

		setupSetVersionExpects();
	}

	private void setupPresenter() {
		presenter = new ApplicationVersionPresenter(view, playerService,shell);
	}

	private void setupInitializationExpects() {
		InOrder inOrder = inOrder(playerService,view,shell);
		inOrder.verify(playerService).getApplicationVersion(presenter.eventUpdateApplicationVersion);
		inOrder.verify(view).setShell(shell);
	}

	private void setupSetVersionExpects() {
		verify(view).setApplicationVersion("v. 42");
	}

}
