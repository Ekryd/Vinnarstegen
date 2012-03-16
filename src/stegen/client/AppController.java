package stegen.client;
import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.*;
import stegen.client.gui.desktop.login.*;
import stegen.client.gui.desktop.player.*;
import stegen.client.gui.desktop.refresh.*;
import stegen.client.gui.desktop.register.*;
import stegen.client.gui.info.*;
import stegen.client.presenter.*;
import stegen.client.service.*;
import stegen.client.service.insult.*;
import stegen.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

public class AppController {
	final EventBus eventBus;
	final UpdateLoginStatusCallback eventCheckLoginStatusHandler = createEventCheckLoginStatusHandler();
	private String hostPageBaseURL;
	final Shell shell;
	final HasWidgets.ForIsWidget parentView;

	private AppController(EventBus eventBus,Shell shell,HasWidgets.ForIsWidget parentView) {
		this.eventBus = eventBus;
		this.shell = shell;
		this.parentView = parentView;
		this.parentView.add(shell);
	}

	public AppController(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService,Shell shell,HasWidgets.ForIsWidget parentView) {		
		this(EventBusImpl.create(playerCommandService, scoreService, playerService),shell,parentView);
	}

	public static AppController createForTest(EventBus eventBus,Shell shell,HasWidgets.ForIsWidget parentView) {
		return new AppController(eventBus,shell,parentView);
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
				new ApplicationVersionPresenter(new ApplicationVersionView(), eventBus,shell).go();

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
		new LoginPresenter((LoginPresenter.Display) GWT.create(LoginPresenter.Display.class), loginData,shell).go();
	}

	private void createRegistrationPresenters(LoginDataDto loginData) {
		new LogoutPresenter(new LogoutView(), loginData,shell).go();
		new RegistrationPresenter(new RegistrationView(), loginData, eventBus, hostPageBaseURL,shell).go();
		new NonregisteredUserPresenter(new UserView(), loginData,shell).go();
	}

	private void createLoggedInPresenters(LoginDataDto loginData) {
		new LogoutPresenter(new LogoutView(), loginData,shell).go();
		new RegisteredUserPresenter(new ChangeNicknameView(), loginData, eventBus,shell).go();
		new CompositeMainPresenter((CompositeMainPresenter.Display)GWT.create(CompositeMainPresenter.Display.class), loginData, eventBus, new InsultFactoryImpl(),new DateTimeFormatsImpl(),shell).go();
		new RefreshPresenter(new RefreshView(), eventBus,shell).go();
	}
}
