package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class RegistrationPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final Display.NewUserPasswordOkKeyPressAndClickHandler checkNewUserPasswordHandler = new NewUserPasswordOkkeyPressAndClickHandler();
	final UpdateIsNewUserPasswordOkCallback eventNewUserPassword = createCommandIsNewUserPasswordCallback();
	private final String hostPageBaseURL;
	private final Shell shell;

	public interface Display {
		String getRegistrationCode();

		void showRegistrationFail();

		void addRegistrationEventHandler(NewUserPasswordOkKeyPressAndClickHandler handler);

		interface NewUserPasswordOkKeyPressAndClickHandler extends KeyPressHandler, ClickHandler {
		}
		void setShell(Shell shell);
	}

	public RegistrationPresenter(Display loginButNotRegisteredView, LoginDataDto loginData, EventBus eventBus,
			String hostPageBaseURL, Shell shell) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
		this.eventBus = eventBus;
		this.hostPageBaseURL = hostPageBaseURL;
		this.shell = shell;
	}

	@Override
	public void go() {
		view.addRegistrationEventHandler(checkNewUserPasswordHandler);
		eventBus.addHandler(eventNewUserPassword);
		view.setShell(shell);
	}

	private UpdateIsNewUserPasswordOkCallback createCommandIsNewUserPasswordCallback() {
		return new UpdateIsNewUserPasswordOkCallback() {

			@Override
			public void onSuccessImpl(Boolean result) {
				if (result) {
					eventBus.registerPlayer(loginData.player.email);
					eventBus.getUserLoginStatus(hostPageBaseURL);
				} else {
					view.showRegistrationFail();
				}
			}
		};
	}

	private class NewUserPasswordOkkeyPressAndClickHandler implements Display.NewUserPasswordOkKeyPressAndClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			checkRegistrationCode();
		}

		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.getCharCode() == KeyCodes.KEY_ENTER) {
				checkRegistrationCode();
			}
		}

		private void checkRegistrationCode() {
			String registrationCode = view.getRegistrationCode();
			eventBus.isNewUserPasswordOk(registrationCode);
		}
	}
}
