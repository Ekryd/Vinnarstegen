package stegen.client;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private LoginDataDto loginData;
	private MessageCentral messageCentral = new MessageCentral();

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
		if (loginData.loginResponse == LoginResult.LOGGED_IN_AND_REGISTERED) {
			showOkLogin();
		}
		if (loginData.loginResponse == LoginResult.LOGGED_IN_GMAIL) {
			showRegisterPanel();
		}
		if (loginData.loginResponse == LoginResult.NOT_LOGGED_IN) {
			showLoginPanel();
		}
	}

	private void showOkLogin() {
		rootPanel().add(new Label("Hej " + loginData.nickname));
		logoutPanel().add(new Anchor("Logga ut", loginData.logoutUrl));
		showLista();
	}

	private void showRegisterPanel() {
		logoutPanel().add(new Anchor("Logga ut", loginData.logoutUrl));
		rootPanel().add(new RegisterPanel("SuckoPust", loginData) {

			@Override
			public void onRegisterOk(LoginDataDto loginData) {
				showLista();
			}
		});
	}

	private void showLoginPanel() {
		// Assemble login panel.
		Anchor signInLink = new Anchor("Sign In", loginData.signInUrl);
		Label loginLabel = new Label("Please sign in to your Google Account to access the Stegen application.");
		VerticalPanel loginPanel = new VerticalPanel();
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		rootPanel().add(loginPanel);
		logoutPanel().clear();
	}

	private void showLista() {
		ScoreCellTable playerTable = new ScoreCellTable(messageCentral, loginData);
		listPanel().add(playerTable);
		listPanel().add(new CleanScoresButton(messageCentral, loginData));
		listPanel().add(new UndoPanel(messageCentral, loginData));
		messageCentral.updateScores();
		messageCentral.updateUndoList();
		messageCentral.updateUndoCommand();
	}

	private RootPanel rootPanel() {
		return RootPanel.get("container");
	}

	private RootPanel logoutPanel() {
		return RootPanel.get("logout");
	}

	private RootPanel listPanel() {
		return RootPanel.get("list");
	}
}
