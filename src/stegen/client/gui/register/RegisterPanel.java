package stegen.client.gui.register;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class RegisterPanel implements IsWidget {

	private final VerticalPanel baseWidget = new VerticalPanel();
	private final Button registeraButton = new Button("Registrera");
	private final Label registerLabel = new Label("Du måste ange 'Stegen-lösenordet' för att registrera dig.");
	private final TextBox kodField = new TextBox();;

	public RegisterPanel() {
		initLayout();
	}

	private void initLayout() {
		registeraButton.setStylePrimaryName("button");
		baseWidget.add(registerLabel);
		baseWidget.add(kodField);
		baseWidget.add(registeraButton);

		// Focus the cursor on the password field when the app loads
		kodField.setFocus(true);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public String getRegistrationText() {
		return kodField.getText();
	}

	public void showRegistrationFail() {
		registerLabel.setText("Fel 'Stegen-lösenord', pröva igen.");
	}

	public void addClickRegistrationHandler(ClickHandler clickHandler) {
		registeraButton.addClickHandler(clickHandler);
	}

	public void addKeyPressHandler(KeyPressHandler keyPressHandler) {
		kodField.addKeyPressHandler(keyPressHandler);
	}
}
