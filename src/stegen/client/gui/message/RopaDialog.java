package stegen.client.gui.message;

import stegen.client.dto.*;
import stegen.client.gui.message.RopaKnapp.*;
import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class RopaDialog extends DialogBox {
	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private final ButtonText buttonText;
	private final Button closeButton = new Button("Avbryt");
	private final Button okButton = new Button("Ok");
	private final TextBox messageBox = new TextBox();

	public RopaDialog(MessageCentral messageCentral, LoginDataDto loginData, ButtonText buttonText) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		this.buttonText = buttonText;
		init();
		setupButtonHandler();
	}

	private void init() {
		VerticalPanel verticalPanel = new VerticalPanel();
		add(verticalPanel);

		HorizontalPanel inputPanel = new HorizontalPanel();
		Label messageLabel = new Label(loginData.nickname + " " + buttonText.actionText);
		inputPanel.add(messageLabel);

		inputPanel.add(messageBox);
		inputPanel.setCellVerticalAlignment(messageLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(inputPanel);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setWidth("100%");
		buttonPanel.add(okButton);
		buttonPanel.add(closeButton);
		buttonPanel.setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_LEFT);
		buttonPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel.add(buttonPanel);

		setWidget(verticalPanel);

	}

	private void setupButtonHandler() {
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				hide();
			}
		});
		okButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				hide();
				messageCentral.sendMessage(loginData.emailAddress, loginData.nickname + " " + buttonText.actionText
						+ " " + messageBox.getText());
			}

		});
	}
}
