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
	final CommandRefreshCallback eventCommandRefreshCallback = createCommandRefreshCallback();
	final CommandUndoCallback eventCommandUndoCommandCallback = createCommandUndoCallback();
	final CommandPlayerWonCallback eventCommandPlayerWonCallback = createCommandPlayerWonCallback();
	final CommandClearScoresCallback eventCommandClearAllScoresCallback = createCommandClearScoresCallback();
	final UpdateUndoCommandCallback eventUpdateUndoCommandCallback = createUpdateUndoCommandCallback();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

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
		eventBus.addHandler(eventCommandUndoCommandCallback);
		eventBus.addHandler(eventCommandPlayerWonCallback);
		eventBus.addHandler(eventCommandClearAllScoresCallback);
		eventBus.addHandler(eventCommandRefreshCallback);
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

	private CommandRefreshCallback createCommandRefreshCallback() {
		return new CommandRefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadUndoButton();
			}
		};
	}

	private CommandClearScoresCallback createCommandClearScoresCallback() {
		return new CommandClearScoresCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadUndoButton();
			}
		};
	}

	private CommandPlayerWonCallback createCommandPlayerWonCallback() {
		return new CommandPlayerWonCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				loadUndoButton();
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(PlayerDto result) {
				loadUndoButton();
			}
		};
	}

	private CommandUndoCallback createCommandUndoCallback() {
		return new CommandUndoCallback() {

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
				return result != null && result.player.email.address.equals(loginData.player.email.address);
			}
		};
	}

}
