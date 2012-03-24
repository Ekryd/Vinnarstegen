package stegen.client.presenter;

import static org.mockito.Mockito.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.presenter.RefreshPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;
@RunWith(MockitoJUnitRunner.class)
public class RefreshPresenterTest {

	private RefreshPresenter presenter;
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;
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
		
		verify(presenter.refreshService).refresh(presenter.refreshCallback);
	}
	
	@Test
	public void shouldFireRefreshEvent(){
		setupPresenter();
		
		presenter.refreshCallback.onSuccess(RefreshType.CHANGES_ON_SERVER_SIDE);
		
		verify(gwtEventBus).fireEvent(any(RefreshEvent.class));
	}

	@Test
	public void testTimerTick() {
		setupPresenter();
		
		presenter.timerCommand.run();
		
		verify(presenter.refreshService).refresh(presenter.refreshCallback);
	}

	private void setupPresenter() {
		presenter = new RefreshPresenter(view, mock(PlayerCommandServiceAsync.class),gwtEventBus,shell);
		presenter.refreshService = mock(RefreshService.class);
	}

	private void setupInitializationExpects() {
		verify(view).addClickRefreshHandler(presenter.clickRefreshHandler);
		verify(view).startTimer(presenter.timerCommand);
		verify(view).setShell(shell);
	}
}
