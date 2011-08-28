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
import stegen.client.service.insult.*;
import stegen.client.service.messageprefix.*;
import stegen.shared.*;

public class AppController {

	final EventBus eventBus;
	final UserLoginStatusCallback eventCheckLoginStatusHandler = createEventCheckLoginStatusHandler();
	private String hostPageBaseURL;

	private AppController(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public AppController(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		this(EventBusImpl.create(playerCommandService, scoreService, playerService));
	}

	public static AppController createForTest(EventBus eventBus) {
		return new AppController(eventBus);
	}

	public void start(String hostPageBaseURL) {
		this.hostPageBaseURL = hostPageBaseURL;
		setupLoginStatusEvent();
		eventBus.getUserLoginStatus(hostPageBaseURL);
	}

	private void setupLoginStatusEvent() {
		eventBus.addHandler(eventCheckLoginStatusHandler);
	}

	private UserLoginStatusCallback createEventCheckLoginStatusHandler() {
		return new UserLoginStatusCallback() {

			@Override
			public void onSuccessImpl(LoginDataDto result) {
				eventBus.clearCallbacks();
				setupLoginStatusEvent();

				switch (result.loginResponse) {
				case NOT_LOGGED_IN:
					createLoginPresenter(result);
					break;
				case LOGGED_IN_GMAIL:
					createRegistrationPresenters(result);
					break;
				case LOGGED_IN_AND_REGISTERED:
					createLoggedInPresenters(result);
					break;
				default:
					createLoginPresenter(result);
				}
			}

		};
	}

	private void createLoginPresenter(LoginDataDto result) {
		new LoginPresenter(new LoginView(), result).go();
	}

	private void createRegistrationPresenters(LoginDataDto result) {
		new LogoutPresenter(new LogoutView(), result).go();
		new RegistrationPresenter(new RegistrationView(), result, eventBus, hostPageBaseURL).go();
		new NonregisteredUserPresenter(new NonregisteredUserView(), result).go();
	}

	private void createLoggedInPresenters(LoginDataDto result) {
		new LogoutPresenter(new LogoutView(), result).go();
		new RegisteredUserPresenter(new RegisteredUserView(), result, eventBus).go();
		new MessagesPresenter(new MessagesView(), result, new MessagePrefixGeneratorImpl(), eventBus).go();
		new CompositeMainPresenter(new CompositeMainView(), result, eventBus, new InsultFactoryImpl()).go();
	}
}
