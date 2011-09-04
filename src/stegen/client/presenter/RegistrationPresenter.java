package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class RegistrationPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final ClickHandler checkRegistrationOkHandler = createCheckRegistrationOkHandler();
	final RegisterPlayerCallback eventRegisterPlayerHandler = createEventRegisterPlayerHandler();
	private final String hostPageBaseURL;

	public interface Display {
		void addClickRegistrationHandler(ClickHandler clickHandler);

		String getRegistrationCode();

		void showRegistrationFail();
	}

	public RegistrationPresenter(Display loginButNotRegisteredView, LoginDataDto loginData, EventBus eventBus,
			String hostPageBaseURL) {
		this.view = loginButNotRegisteredView;
		this.loginData = loginData;
		this.eventBus = eventBus;
		this.hostPageBaseURL = hostPageBaseURL;
	}

	private ClickHandler createCheckRegistrationOkHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String registrationCode = view.getRegistrationCode();
				if (registrationCode.equals("SuckoPust")) {
					eventBus.registerPlayer(loginData.player.email);
				} else {
					view.showRegistrationFail();
				}
			}
		};
	}

	@Override
	public void go() {
		view.addClickRegistrationHandler(checkRegistrationOkHandler);
		eventBus.addHandler(eventRegisterPlayerHandler);
	}

	private RegisterPlayerCallback createEventRegisterPlayerHandler() {
		return new RegisterPlayerCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				eventBus.getUserLoginStatus(hostPageBaseURL);
			}
		};
	}

}
