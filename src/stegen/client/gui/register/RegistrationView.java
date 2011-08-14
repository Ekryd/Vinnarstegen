package stegen.client.gui.register;

import static stegen.client.gui.BaseHtmlPage.*;
import stegen.client.presenter.RegistrationPresenter.Display;

import com.google.gwt.event.dom.client.*;

public class RegistrationView implements Display {

	private final RegisterPanel2 registerPanel = new RegisterPanel2();

	public RegistrationView() {
		MAIN_AREA.clearPanel();
		MAIN_AREA.addToPanel(registerPanel);
	}

	@Override
	public void addClickRegistrationHandler(ClickHandler clickHandler) {
		registerPanel.addClickRegistrationHandler(clickHandler);
	}

	@Override
	public String getRegistrationCode() {
		return registerPanel.getRegistrationText();
	}

	@Override
	public void showRegistrationFail() {
		registerPanel.showRegistrationFail();
	}
}