package stegen.client;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.*;
import stegen.client.gui.desktop.login.*;
import stegen.client.gui.info.*;
import stegen.client.gui.player.*;
import stegen.client.gui.refresh.*;
import stegen.client.gui.register.*;
import stegen.client.presenter.*;
import stegen.client.service.*;
import stegen.client.service.insult.*;
import stegen.shared.*;

import com.google.gwt.core.client.*;

public class AppController {
	final EventBus eventBus;
	final UpdateLoginStatusCallback eventCheckLoginStatusHandler = createEventCheckLoginStatusHandler();
	private String hostPageBaseURL;
	final private Shell shell;

	private AppController(EventBus eventBus,Shell shell) {
		this.eventBus = eventBus;
		this.shell = shell;
	}

	public AppController(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService,Shell shell) {		
		this(EventBusImpl.create(playerCommandService, scoreService, playerService),
				shell);
	}

	public static AppController createForTest(EventBus eventBus) {
		return new AppController(eventBus,null);
	}

	
	public void start(String hostPageBaseURL) {
		this.hostPageBaseURL = hostPageBaseURL;
		setupLoginStatusEvent();
		eventBus.getUserLoginStatus(hostPageBaseURL);
	}

	private void setupLoginStatusEvent() {
		eventBus.addHandler(eventCheckLoginStatusHandler);
	}

	private UpdateLoginStatusCallback createEventCheckLoginStatusHandler() {
		return new UpdateLoginStatusCallback() {

			@Override
			public void onSuccessImpl(LoginDataDto loginData) {
				eventBus.clearCallbacks();
				setupLoginStatusEvent();
				new ApplicationVersionPresenter(new ApplicationVersionView(), eventBus).go();

				switch (loginData.loginResponse) {
				case NOT_LOGGED_IN:
					createLoginPresenter(loginData);
					break;
				case LOGGED_IN_GMAIL:
					createRegistrationPresenters(loginData);
					break;
				case LOGGED_IN_AND_REGISTERED:
					createLoggedInPresenters(loginData);
					break;
				default:
					createLoginPresenter(loginData);
				}
			}

		};
	}

	private void createLoginPresenter(LoginDataDto loginData) {
		LoginPresenter.Display loginView = GWT.create(LoginPresenter.Display.class);
		loginView.setShell(shell);
		new LoginPresenter(loginView, loginData).go();
	}

	private void createRegistrationPresenters(LoginDataDto loginData) {
		new LogoutPresenter(new LogoutView(), loginData).go();
		new RegistrationPresenter(new RegistrationView(), loginData, eventBus, hostPageBaseURL).go();
		new NonregisteredUserPresenter(new NonregisteredUserView(), loginData).go();
	}

	private void createLoggedInPresenters(LoginDataDto loginData) {
		new LogoutPresenter(new LogoutView(), loginData).go();
		new RegisteredUserPresenter(new RegisteredUserView(), loginData, eventBus).go();
		new CompositeMainPresenter(new CompositeMainView(), loginData, eventBus, new InsultFactoryImpl(),
				new DateTimeFormatsImpl()).go();
		new RefreshPresenter(new RefreshView(), eventBus).go();
	}
}
