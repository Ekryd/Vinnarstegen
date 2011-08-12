package stegen.client.presenter;

import stegen.client.event.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class RegistrationPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	final ClickHandler checkRegistrationOkHandler;

	public interface Display {
		void addClickRegistrationHandler(ClickHandler clickHandler);

		String getRegistrationCode();

		void showRegistrationFail();
	}

	public RegistrationPresenter(Display loginButNotRegisteredView, LoginDataDto result, EventBus eventBus) {
		this.view = loginButNotRegisteredView;
		this.result = result;
		this.eventBus = eventBus;
		checkRegistrationOkHandler = createCheckRegistrationOkHandler();
	}

	private ClickHandler createCheckRegistrationOkHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String registrationCode = view.getRegistrationCode();
				if (registrationCode.equals("SuckoPust")) {
					eventBus.registerPlayer(result.player.email);
				} else {
					view.showRegistrationFail();
				}
			}
		};
	}

	@Override
	public void go() {
		view.addClickRegistrationHandler(checkRegistrationOkHandler);
	}

}
