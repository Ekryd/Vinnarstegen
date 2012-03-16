package stegen.client.presenter;

import static org.mockito.Mockito.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.RefreshPresenter.Display;
@RunWith(MockitoJUnitRunner.class)
public class RefreshPresenterTest {

	private RefreshPresenter presenter;
	@Mock
	private EventBus eventBus;
	@Mock
	private Display view;
	@Mock
	private Shell shell;



	@Test
	public void testShowView() {
		setupPresenter();

		presenter.go();
		
		setupInitializationExpects();
	}

	@Test
	public void testRefreshCallback() {
		setupPresenter();

		presenter.clickRefreshHandler.onClick(null);
		
		verify(eventBus).refresh();
	}

	@Test
	public void testTimerTick() {
		setupPresenter();
		
		presenter.timerCommand.run();
		
		verify(eventBus).refresh();
	}

	private void setupPresenter() {
		presenter = new RefreshPresenter(view, eventBus,shell);
	}

	private void setupInitializationExpects() {
		verify(view).addClickRefreshHandler(presenter.clickRefreshHandler);
		verify(view).startTimer(presenter.timerCommand);
		verify(view).setShell(shell);
	}
}
