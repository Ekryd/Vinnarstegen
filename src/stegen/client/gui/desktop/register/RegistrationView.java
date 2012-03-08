package stegen.client.gui.desktop.register;
import com.google.gwt.core.client.*;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

import stegen.client.gui.*;
import stegen.client.gui.desktop.login.*;
import stegen.client.gui.desktop.login.LoginView.*;
import stegen.client.presenter.RegistrationPresenter.Display;

public class RegistrationView  extends Composite implements Display {

	private static RegistrationUiBinder uiBinder = GWT.create(RegistrationUiBinder.class);

	interface RegistrationUiBinder extends UiBinder<Widget, RegistrationView> {}

	@UiField
	Button registeraButton;
	@UiField
	Label registerLabel;
	@UiField
	TextBox kodField;

	public RegistrationView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public String getRegistrationCode() {
		return kodField.getText();
	}

	@Override
	public void showRegistrationFail() {
		registerLabel.setText("Fel 'Stegen-lösenord', pröva igen.");
	}

	@Override
	public void addRegistrationEventHandler(NewUserPasswordOkKeyPressAndClickHandler handler) {
		registeraButton.addClickHandler(handler);
		kodField.addKeyPressHandler(handler);		
	}
	
	@Override
	public void setShell(Shell shell) {
		shell.showInMainArea(this);
	}
}
