package stegen.client;
import stegen.client.event.*;
import stegen.client.gui.*;
import stegen.client.gui.desktop.login.*;
import stegen.client.gui.desktop.player.*;
import stegen.client.gui.desktop.refresh.*;
import stegen.client.gui.desktop.register.*;
import stegen.client.gui.info.*;
import stegen.client.presenter.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public class AppController {
	private final PlayerCommandServiceAsync playerCommandService;
	private final ScoreServiceAsync scoreService;
	private final PlayerServiceAsync playerService;
	final com.google.gwt.event.shared.EventBus gwtEventBus;
	final AsyncCallback<LoginDataDto> userLoginStatusCallback = createLoginStatusCallback();
	private final LoginEventHandler loginStatusHandler = createLoginStatusHandler();
	private String hostPageBaseURL;
	final Shell shell;
	final HasWidgets.ForIsWidget parentView;

	private AppController(Shell shell,HasWidgets.ForIsWidget parentView, com.google.gwt.event.shared.EventBus gwtEventBus,PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		this.shell = shell;
		this.parentView = parentView;
		this.parentView.add(shell);
		this.gwtEventBus = gwtEventBus;
		this.playerCommandService = playerCommandService;
		this.scoreService = scoreService;
		this.playerService = playerService;
	}
	
	public AppController(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService,Shell shell,HasWidgets.ForIsWidget parentView,com.google.gwt.event.shared.EventBus gwtEventBus) {		
		this(shell,parentView,gwtEventBus,playerCommandService,scoreService,playerService);
	}
	
	public void start(String hostPageBaseURL) {
		this.hostPageBaseURL = hostPageBaseURL;
		gwtEventBus.addHandler(LoginEvent.TYPE,loginStatusHandler);
		playerService.getUserLoginStatus(hostPageBaseURL,userLoginStatusCallback);
	}

	private AbstractAsyncCallback<LoginDataDto> createLoginStatusCallback(){
		return new AbstractAsyncCallback<LoginDataDto>(){
			@Override
			public void onSuccess(LoginDataDto result) {
				gwtEventBus.fireEvent(new LoginEvent(result));							
			}
		};
	}
	private LoginEventHandler createLoginStatusHandler(){
		return new LoginEventHandler() {
			@Override
			public void handleEvent(LoginEvent event) {
				new ApplicationVersionPresenter(new ApplicationVersionView(),playerService, shell).go();
				LoginDataDto loginData = event.getLoginData();
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
		new RegistrationPresenter(new RegistrationView(), loginData, playerService, gwtEventBus,hostPageBaseURL,shell).go();
		new NonregisteredUserPresenter(new UserView(), loginData,shell).go();
	}

	private void createLoggedInPresenters(LoginDataDto loginData) {
		new LogoutPresenter(new LogoutView(), loginData,shell).go();
		new RegisteredUserPresenter(new ChangeNicknameView(), loginData, playerService,gwtEventBus,shell).go();
		new CompositeMainPresenter((CompositeMainPresenter.Display)GWT.create(CompositeMainPresenter.Display.class), loginData,gwtEventBus, playerCommandService, scoreService,playerService,new DateTimeFormatsImpl(),shell).go();
		new RefreshPresenter(new RefreshView(), playerCommandService,gwtEventBus,shell).go();
	}
}
