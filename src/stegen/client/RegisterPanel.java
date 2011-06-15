package stegen.client;

import stegen.client.dto.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public abstract class RegisterPanel extends VerticalPanel {
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private final String password;
	private final LoginDataDto loginData;

	private Button registeraButton;
	private Label registerLabel;
	private TextBox kodField;

	public RegisterPanel(String password, LoginDataDto loginData) {
		this.password = password;
		this.loginData = loginData;
		initComponents();
		initButtonHandler();
	}

	private void initComponents() {
		registerLabel = new Label("Hej " + loginData.nickname
				+ ". Du måste skriva in den magiska koden för att registrera dig.");
		registeraButton = new Button("Registrera");
		kodField = new TextBox();
		kodField.setText("<kod>");

		// Focus the cursor on the name field when the app loads
		kodField.setFocus(true);
		kodField.selectAll();
		add(registerLabel);
		add(kodField);
		add(registeraButton);
	}

	private void initButtonHandler() {
		registeraButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (kodField.getText().equals(password)) {
					loginService.registerPlayer(loginData.emailAddress, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							registerLabel.setText("Ja, " + loginData.nickname + ". Du är nu registrerad.");
							registeraButton.setVisible(false);
							kodField.setVisible(false);
							onRegisterOk(loginData);
						}

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("onFailure");
						}
					});
				} else {
					registerLabel.setText("Nej, " + loginData.nickname + ". Det där gick inte bra. Skriv in koden.");
				}

			}
		});
	}

	protected abstract void onRegisterOk(LoginDataDto loginData);
}
