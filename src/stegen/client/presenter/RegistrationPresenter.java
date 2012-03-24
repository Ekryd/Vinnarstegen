package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;

public class RegistrationPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final PlayerServiceAsync playerService;
	private final com.google.gwt.event.shared.EventBus gwtEventBus;
	final Display.KeyPressAndClickHandler checkNewUserPasswordHandler = new NewUserPasswordOkkeyPressAndClickHandler();
	final AsyncCallback<LoginDataDto> userLoginStatusCallback = new UpdateLoginStatusCallback();
	final AsyncCallback<Void> registerPlayerCallback = new RegisterPlayerCallback();
	final AsyncCallback<Boolean> isNewUserPasswordOk = new IsNewUserPasswordOkCallback();
	private final String hostPageBaseURL;
	private final Shell shell;

	public interface Display {
		String getRegistrationCode();

		void showRegistrationFail();

		void addRegistrationEventHandler(KeyPressAndClickHandler handler);

		interface KeyPressAndClickHandler extends KeyPressHandler, ClickHandler {
		}
		void setShell(Shell shell);
	}

	public RegistrationPresenter(Display loginButNotRegisteredView, LoginDataDto loginData, PlayerServiceAsync playerService,com.google.gwt.event.shared.EventBus gwtEventBus,
			String hostPageBaseURL, Shell shell) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
		this.playerService = playerService;
		this.hostPageBaseURL = hostPageBaseURL;
		this.shell = shell;
		this.gwtEventBus = gwtEventBus;
	}

	@Override
	public void go() {
		view.addRegistrationEventHandler(checkNewUserPasswordHandler);
		view.setShell(shell);
	}

	
	private class UpdateLoginStatusCallback extends AbstractAsyncCallback<LoginDataDto>  {
		@Override
		public void onSuccess(LoginDataDto result) {
			gwtEventBus.fireEvent(new LoginEvent(result));							
		}
	}
	private class RegisterPlayerCallback extends AbstractAsyncCallback<Void>  {
		@Override
		public void onSuccess(Void result) {
		}
	}
	
	private class IsNewUserPasswordOkCallback extends AbstractAsyncCallback<Boolean>{
		@Override
		public void onSuccess(Boolean result) {
			if (result) {
				playerService.registerPlayer(loginData.player.email, registerPlayerCallback);
				playerService.getUserLoginStatus(hostPageBaseURL, userLoginStatusCallback);
			} else {
				view.showRegistrationFail();
			}	
		}
	}
	
	private class NewUserPasswordOkkeyPressAndClickHandler implements Display.KeyPressAndClickHandler {
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
			playerService.isNewUserPasswordOk(registrationCode,isNewUserPasswordOk);
		}
	}
}
