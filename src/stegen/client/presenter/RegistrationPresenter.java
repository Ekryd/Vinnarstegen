package stegen.client.presenter;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class RegistrationPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final ClickHandler checkNUPHandler = createIsNUPHandler();
	final CommandIsNUPCallback eventNUP = createCommandIsNUPCallback();
	final KeyPressHandler nupKeyhandler = createNUPKeyHandler();
	private final String hostPageBaseURL;

	public interface Display {
		void addClickRegistrationHandler(ClickHandler clickHandler);
		
		public void addKeyPressHandler(KeyPressHandler keyPressHandler);

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

	
	private ClickHandler createIsNUPHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String registrationCode = view.getRegistrationCode();
				eventBus.isNUP(registrationCode);
			}
		};
	}
	
	private KeyPressHandler createNUPKeyHandler() {
		return new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if (event.getCharCode() == KeyCodes.KEY_ENTER) {
					String registrationCode = view.getRegistrationCode();
					eventBus.isNUP(registrationCode);
				}
			}
		};
	}
	
	
	

	@Override
	public void go() {
		
		view.addClickRegistrationHandler(checkNUPHandler);
		view.addKeyPressHandler(nupKeyhandler);
		eventBus.addHandler(eventNUP);
	}

	
	private CommandIsNUPCallback createCommandIsNUPCallback() {
		return new CommandIsNUPCallback() {
			
			@Override
			public void onSuccessImpl(Boolean result) {
				if( result ){
					eventBus.registerPlayer(loginData.player.email);
					eventBus.getUserLoginStatus(hostPageBaseURL);
				}else{
					view.showRegistrationFail();
				}
			}
		};

	}

}
