package stegen.client.gui.register;

import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.*;
import com.google.gwt.user.client.ui.*;

public abstract class RegisterPanel extends VerticalPanel {
	private final PlayerServiceAsync loginService = GWT.create(PlayerService.class);
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
		registerLabel = new Label("Du måste skriva in den magiska koden för att registrera dig.");
		registeraButton = new Button("Registrera");
		registeraButton.setStylePrimaryName("button");

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
					loginService.registerPlayer(loginData.player.email, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result) {
							registerLabel.setText("Ja, " + loginData.player.nickname + ". Du är nu registrerad.");
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
					registerLabel.setText("Nej, " + loginData.player.nickname
							+ ". Det där gick inte bra. Skriv in koden.");
				}

			}
		});
	}

	protected abstract void onRegisterOk(LoginDataDto loginData);
}
