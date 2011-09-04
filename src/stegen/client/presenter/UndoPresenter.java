package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class UndoPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final ClickHandler clickUndoInputHandler = createClickUndoHandler();
	final RefreshCallback eventRefreshCallback = createRefreshCallback();
	final UndoCallback eventUndoCommandCallback = createUndoCallback();
	final PlayerWonCallback eventPlayerWonCallback = createPlayerWonCallback();
	final ClearScoresCallback eventClearAllScoresCallback = createClearScoresCallback();
	final UpdateUndoCommandCallback eventUpdateUndoCommandCallback = createUpdateUndoCommandCallback();;

	public interface Display {

		void setUndoButtonText(String buttonText);

		void addClickUndoHandler(ClickHandler clickHandler);

		void showUndoFailAlert();

		void hideUndoButton();

		void showUndoButton();

	}

	public UndoPresenter(Display messagesView, LoginDataDto loginData, EventBus eventBus) {
		this.view = messagesView;
		this.loginData = loginData;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
		initEvents();
		loadUndoButton();
	}

	private void initView() {
		view.addClickUndoHandler(clickUndoInputHandler);
	}

	private void initEvents() {
		eventBus.addHandler(eventUpdateUndoCommandCallback);
		eventBus.addHandler(eventUndoCommandCallback);
		eventBus.addHandler(eventPlayerWonCallback);
		eventBus.addHandler(eventClearAllScoresCallback);
		eventBus.addHandler(eventRefreshCallback);
	}

	private void loadUndoButton() {
		eventBus.updateUndoCommand();
	}

	private ClickHandler createClickUndoHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				eventBus.undoPlayerCommand(loginData.player);
			}
		};
	}

	private RefreshCallback createRefreshCallback() {
		return new RefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadUndoButton();
			}
		};
	}

	private ClearScoresCallback createClearScoresCallback() {
		return new ClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadUndoButton();
			}
		};
	}

	private PlayerWonCallback createPlayerWonCallback() {
		return new PlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadUndoButton();
			}
		};
	}

	private UndoCallback createUndoCallback() {
		return new UndoCallback() {

			@Override
			public void onSuccessImpl(UndoPlayerCommandResult result) {
				if (result == UndoPlayerCommandResult.FAILURE) {
					view.showUndoFailAlert();
				}
				loadUndoButton();
			}
		};
	}

	private UpdateUndoCommandCallback createUpdateUndoCommandCallback() {
		return new UpdateUndoCommandCallback() {

			@Override
			public void onSuccessImpl(PlayerCommandDto result) {
				if (ownsLastUndoCommand(result)) {
					view.setUndoButtonText("Ångra " + result.description);
					view.showUndoButton();
				} else {
					view.hideUndoButton();
				}
			}

			private boolean ownsLastUndoCommand(PlayerCommandDto result) {
				return result != null && result.player.email.equals(loginData.player.email);
			}
		};
	}

}
