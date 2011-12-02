package stegen.client.gui.register;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.RegistrationPresenter.Display;

public class RegistrationView implements Display {

	private final RegisterPanel registerPanel = new RegisterPanel();

	public RegistrationView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(registerPanel);
	}

	@Override
	public String getRegistrationCode() {
		return registerPanel.getRegistrationText();
	}

	@Override
	public void showRegistrationFail() {
		registerPanel.showRegistrationFail();
	}

	@Override
	public void addRegistrationEventHandler(NewUserPasswordOkKeyPressAndClickHandler handler) {
		registerPanel.addClickRegistrationHandler(handler);
		registerPanel.addKeyPressHandler(handler);		
	}
}
