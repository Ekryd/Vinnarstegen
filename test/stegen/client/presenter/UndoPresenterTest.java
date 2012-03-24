package stegen.client.presenter;

import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;

import stegen.client.event.*;
import stegen.client.presenter.UndoPresenter.Display;
import stegen.client.service.*;
import stegen.shared.*;

@RunWith(MockitoJUnitRunner.class)
public class UndoPresenterTest {
	@Mock
	com.google.gwt.event.shared.EventBus gwtEventBus;
	@Mock
	private PlayerCommandServiceAsync playerService;
	@Mock
	private Display view;
	private LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
	private UndoPresenter presenter;

	@Test
	public void testShowView() {
		setupPresenter();
		
		presenter.go();
		
		setupInitializationExpects();

	}

	@Test
	public void testUndoCommand() {
		setupPresenter();
		presenter.go();
		
		simulateUndoCommand();
		setupUndoCommandOkExpects();

		
	}

	@Test
	public void testSimpleCallbacks() {
		setupPresenter();

		
		presenter.clearScoresEventHandler.handleEvent(null);
		verify(playerService).getUndoCommand(presenter.getUndoCallback);
		
		reset(playerService);
		
		presenter.gamePlayedEventHandler.handleEvent(null);
		verify(playerService).getUndoCommand(presenter.getUndoCallback);
		reset(playerService);
		
		presenter.refreshEventHandler.handleEvent(new RefreshEvent(RefreshType.CHANGES_ON_SERVER_SIDE));
		verify(playerService).getUndoCommand(presenter.getUndoCallback);
		reset(playerService);
	}

	@Test
	public void testOnUndoFailCallback() {
		setupPresenter();
		
		presenter.undoCallback.onSuccess(UndoPlayerCommandResult.FAILURE);
		verify(gwtEventBus).fireEvent(any(UndoEvent.class));
	}

	@Test
	public void testOnUpdateUndoCommandCallbackNotOwned() {
		setupPresenter();
		
		presenter.getUndoCallback.onSuccess(new PlayerCommandDto(LoginDataDtoFactory.createOtherLoginData().player, new Date(), "desc"));
		
		verify(view).hideUndoButton();

	}

	@Test
	public void testOnUpdateUndoCommandCallbackIsOwned() {
		setupPresenter();

		presenter.getUndoCallback.onSuccess(new PlayerCommandDto(loginData.player, new Date(),"desc"));
		
		verify(view).setUndoButtonText("Ångra desc");
		verify(view).showUndoButton();
	}

	private void setupPresenter() {
		presenter = new UndoPresenter(view, loginData,playerService,gwtEventBus);
	}

	private void setupInitializationExpects() {
		verify(view).addClickUndoHandler(presenter.clickUndoInputHandler);
		verify(gwtEventBus).addHandler(GamePlayedEvent.TYPE, presenter.gamePlayedEventHandler);
		verify(gwtEventBus).addHandler(ClearScoresEvent.TYPE,presenter.clearScoresEventHandler);
		verify(gwtEventBus).addHandler(RefreshEvent.TYPE,presenter.refreshEventHandler);
		verify(gwtEventBus).addHandler(UndoEvent.TYPE, presenter.undoEventHandler);
		verify(playerService).getUndoCommand(presenter.getUndoCallback);
	}

	private void simulateUndoCommand() {
		presenter.clickUndoInputHandler.onClick(null);
	}

	private void setupUndoCommandOkExpects() {
		playerService.undoPlayerCommand(loginData.player,presenter.undoCallback);

	}

}
