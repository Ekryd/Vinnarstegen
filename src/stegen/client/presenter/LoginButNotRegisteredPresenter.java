package stegen.client.presenter;

import stegen.client.event.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class LoginButNotRegisteredPresenter implements Presenter {

	private final Display loginButNotRegisteredView;
	private final LoginDataDto result;
	private final EventBus eventBus;
	final ClickHandler checkRegistrationOkHandler;

	public interface Display {

		void setLogoutUrl(String logoutUrl);

		void setUserName(String name);

		void addClickRegistrationHandler(ClickHandler clickHandler);

		String getRegistrationCode();

		void showRegistrationFail();
	}

	public LoginButNotRegisteredPresenter(Display loginButNotRegisteredView, LoginDataDto result, EventBus eventBus) {
		this.loginButNotRegisteredView = loginButNotRegisteredView;
		this.result = result;
		this.eventBus = eventBus;
		checkRegistrationOkHandler = createCheckRegistrationOkHandler();
	}

	private ClickHandler createCheckRegistrationOkHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String registrationCode = loginButNotRegisteredView.getRegistrationCode();
				if (registrationCode.equals("SuckoPust")) {
					eventBus.registerPlayer(result.player.email);
				} else {
					loginButNotRegisteredView.showRegistrationFail();
				}
			}
		};
	}

	@Override
	public void go() {
		loginButNotRegisteredView.addClickRegistrationHandler(checkRegistrationOkHandler);
		loginButNotRegisteredView.setLogoutUrl(result.logoutUrl);
		loginButNotRegisteredView.setUserName(result.player.nickname);
	}

}
