package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.ApplicationVersionPresenter.Display;
@RunWith(MockitoJUnitRunner.class)
public class ApplicationVersionPresenterTest {

	@Mock
	private Display view;
	@Mock
	private EventBus eventBus;
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
		presenter = new ApplicationVersionPresenter(view, eventBus,shell);
	}

	private void setupInitializationExpects() {
		InOrder inOrder = inOrder(eventBus,view,shell);
		inOrder.verify(eventBus).addHandler(presenter.eventUpdateApplicationVersion);
		inOrder.verify(eventBus).getApplicationVersion();
		inOrder.verify(view).setShell(shell);
	}

	private void setupSetVersionExpects() {
		verify(view).setApplicationVersion("v. 42");
	}

}
