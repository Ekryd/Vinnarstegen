package stegen.client;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.*;
import stegen.client.gui.login.*;
import stegen.client.gui.message.*;
import stegen.client.gui.player.*;
import stegen.client.gui.register.*;
import stegen.client.presenter.*;
import stegen.client.service.*;
import stegen.client.service.messageprefix.*;
import stegen.shared.*;

public class AppController {

	private final EventBus eventBus;

	public AppController(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		eventBus = EventBusImpl.create(playerCommandService, scoreService, playerService);
		bindEvents();
	}

	private void bindEvents() {
		eventBus.addHandler(new CheckUserLoginStatusCallback() {

			@Override
			public void onSuccessImpl(LoginDataDto result) {
				switch (result.loginResponse) {
				case NOT_LOGGED_IN:
					new LoginPresenter(new LoginView(), result).go();
					break;
				case LOGGED_IN_GMAIL:
					new LogoutPresenter(new LogoutView(), result).go();
					new RegistrationPresenter(new RegistrationView(), result, eventBus).go();
					new NonregisteredUserPresenter(new NonregisteredUserView(), result).go();
					break;
				case LOGGED_IN_AND_REGISTERED:
					new LogoutPresenter(new LogoutView(), result).go();
					new RegisteredUserPresenter(new RegisteredUserView(), result, eventBus).go();
					new MessagesPresenter(new MessagesView(), result, new MessagePrefixGeneratorImpl(), eventBus).go();
					new CompositeMainPresenter(new CompositeMainView(), result, eventBus).go();
					// new LoggedInPresenter(new LoggedInView(), result,
					// eventBus).go();
					break;
				default:
					new LoginPresenter(new LoginView(), result).go();
				}
			}

		});
	}

	public void start(String hostPageBaseURL) {
		eventBus.checkUserLoginStatus(hostPageBaseURL);
	}
}
