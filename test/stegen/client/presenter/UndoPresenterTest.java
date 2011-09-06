package stegen.client.presenter;

import static org.easymock.EasyMock.*;

import java.util.*;

import org.junit.*;

import stegen.client.event.*;
import stegen.client.presenter.UndoPresenter.Display;
import stegen.shared.*;

public class UndoPresenterTest {

	private Display view;
	private LoginDataDto loginData;
	private EventBus eventBus;
	private UndoPresenter presenter;

	@Test
	public void testShowView() {
		setupPresenter();

		setupInitializationExpects();

		presenter.go();
	}

	@Test
	public void testUndoCommand() {
		setupPresenter();
		presenter.go();
		setupUndoCommandOkExpects();

		simulateUndoCommand();
	}

	@Test
	public void testSimpleCallbacks() {
		setupPresenter();

		eventBus.updateUndoCommand();
		replay(view, eventBus);
		presenter.eventClearAllScoresCallback.onSuccessImpl(null);
		verify(view, eventBus);
		reset(view, eventBus);

		eventBus.updateUndoCommand();
		replay(view, eventBus);
		presenter.eventPlayerWonCallback.onSuccessImpl(null);
		verify(view, eventBus);
		reset(view, eventBus);

		eventBus.updateUndoCommand();
		replay(view, eventBus);
		presenter.eventRefreshCallback.onSuccessImpl(null);
		verify(view, eventBus);
		reset(view, eventBus);
	}

	@Test
	public void testOnUndoFailCallback() {
		setupPresenter();

		view.showUndoFailAlert();
		eventBus.updateUndoCommand();
		replay(view, eventBus);
		presenter.eventUndoCommandCallback.onSuccessImpl(UndoPlayerCommandResult.FAILURE);
		verify(view, eventBus);
		reset(view, eventBus);
	}

	@Test
	public void testOnUpdateUndoCommandCallbackNotOwned() {
		setupPresenter();

		view.hideUndoButton();
		replay(view, eventBus);
		presenter.eventUpdateUndoCommandCallback.onSuccessImpl(new PlayerCommandDto(LoginDataDtoFactory
				.createOtherLoginData().player, new Date(), "desc"));
		verify(view, eventBus);
		reset(view, eventBus);
	}

	@Test
	public void testOnUpdateUndoCommandCallbackIsOwned() {
		setupPresenter();

		view.setUndoButtonText("Ångra desc");
		view.showUndoButton();
		replay(view, eventBus);
		presenter.eventUpdateUndoCommandCallback.onSuccessImpl(new PlayerCommandDto(loginData.player, new Date(),
				"desc"));
		verify(view, eventBus);
		reset(view, eventBus);
	}

	private void setupPresenter() {
		loginData = LoginDataDtoFactory.createLoginData();
		view = createStrictMock(Display.class);
		eventBus = createStrictMock(EventBus.class);
		presenter = new UndoPresenter(view, loginData, eventBus);
	}

	private void setupInitializationExpects() {
		view.addClickUndoHandler(presenter.clickUndoInputHandler);
		eventBus.addHandler(presenter.eventUpdateUndoCommandCallback);
		eventBus.addHandler(presenter.eventUndoCommandCallback);
		eventBus.addHandler(presenter.eventPlayerWonCallback);
		eventBus.addHandler(presenter.eventClearAllScoresCallback);
		eventBus.addHandler(presenter.eventRefreshCallback);
		eventBus.updateUndoCommand();
		replay(view, eventBus);
	}

	private void simulateUndoCommand() {
		presenter.clickUndoInputHandler.onClick(null);
	}

	private void setupUndoCommandOkExpects() {
		reset(view, eventBus);
		eventBus.undoPlayerCommand(loginData.player);
		replay(view, eventBus);
	}

}
