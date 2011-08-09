package stegen.client;

import stegen.client.event.*;
import stegen.client.messages.*;
import stegen.client.presenter.*;
import stegen.client.service.*;
import stegen.client.view.*;
import stegen.shared.*;

public class AppController {

	private final EventBus eventBus;

	public AppController(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		eventBus = new EventBusImpl(playerCommandService, scoreService, playerService);
		bindEvents();
	}

	private void bindEvents() {
		eventBus.addHandler(Event.CHECK_USER_LOGIN_STATUS, new DefaultCallback<LoginDataDto>() {

			@Override
			public void onSuccess(LoginDataDto result) {
				Presenter presenter = getLoginResponsePresenter(result);
				presenter.go();
			}

			private Presenter getLoginResponsePresenter(LoginDataDto result) {
				switch (result.loginResponse) {
				case NOT_LOGGED_IN:
					return new NotLoggedInPresenter(new NotLoggedInView(), result);
				case LOGGED_IN_GMAIL:
					return new LoginButNotRegisteredPresenter(new LoginButNotRegisteredView(), result, eventBus);
				case LOGGED_IN_AND_REGISTERED:
					return new LoggedInPresenter(new LoggedInView(), result, eventBus);
				default:
					return new NotLoggedInPresenter(new NotLoggedInView(), result);
				}
			}
		});
	}

	public void start(String hostPageBaseURL) {
		eventBus.checkUserLoginStatus(hostPageBaseURL);
	}
}
