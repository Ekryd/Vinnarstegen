package stegen.client;

import stegen.client.dto.*;
import stegen.client.gui.*;
import stegen.client.gui.login.*;
import stegen.client.gui.message.*;
import stegen.client.gui.register.*;
import stegen.client.messages.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private LoginDataDto loginData;
	private MessageCentral messageCentral = new MessageCentral();
	private Timer timer;

	/** This is the entry point method. */
	@Override
	public void onModuleLoad() {
		loginService.userLoginStatus(GWT.getHostPageBaseURL(), new AsyncCallback<LoginDataDto>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("onFailure");
			}

			@Override
			public void onSuccess(LoginDataDto result) {
				loginData = result;
				onLoginPageStart();
			}

		});
	}

	private void onLoginPageStart() {
		if (loginData.loginResponse == LoginResult.NOT_LOGGED_IN) {
			showLoginPanel();
		}
		if (loginData.loginResponse == LoginResult.LOGGED_IN_GMAIL) {
			showRegisterPanel();
		}
		if (loginData.loginResponse == LoginResult.LOGGED_IN_AND_REGISTERED) {
			showOkLogin();
		}
	}

	private void showLoginPanel() {
		showMainArea(new LoginPanel(loginData.signInUrl));
	}

	private void showRegisterPanel() {
		showLogout();
		showUser();
		showMainArea(new RegisterPanel("SuckoPust", loginData) {

			@Override
			public void onRegisterOk(LoginDataDto loginData) {
				showOkLogin();
			}
		});
	}

	private void showOkLogin() {
		showLogout();
		showUser();
		messagesArea().add(new RopaKnapp(messageCentral, loginData));
		refreshArea().add(new RefreshButton(messageCentral));
		showMainArea(new MainContentTabs(messageCentral, loginData));
		addUpdateTimer();
	}

	private void showLogout() {
		logoutArea().clear();
		Anchor link = new Anchor("Logga ut", loginData.logoutUrl);
		logoutArea().add(link);
	}

	private void showUser() {
		userArea().clear();
		userArea().add(new UserPanel(messageCentral, loginData));
	}

	protected void showMainArea(Widget panel) {
		mainArea().clear();
		mainArea().add(panel);
	}

	private void addUpdateTimer() {
		messageCentral.updateScores();
		messageCentral.updateUndoList();
		messageCentral.updateUndoCommand();
		timer = new Timer() {

			@Override
			public void run() {
				messageCentral.updateScores();
				messageCentral.updateUndoList();
				messageCentral.updateUndoCommand();
			}
		};
		timer.scheduleRepeating(1000 * 60);
	}

	private RootPanel mainArea() {
		return RootPanel.get("mainArea");
	}

	private RootPanel logoutArea() {
		return RootPanel.get("logoutArea");
	}

	private RootPanel userArea() {
		return RootPanel.get("userArea");
	}

	private RootPanel messagesArea() {
		return RootPanel.get("messagesArea");
	}

	private RootPanel refreshArea() {
		return RootPanel.get("refreshArea");
	}
}
