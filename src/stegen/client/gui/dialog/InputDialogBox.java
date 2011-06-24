package stegen.client.gui.dialog;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public abstract class InputDialogBox extends DialogBox {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private final Button closeButton = new Button("Avbryt");
	private final Button okButton = new Button("Ok");
	private final TextBox messageBox = new TextBox();
	private Label messageLabel;

	public InputDialogBox(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
		setupButtonHandler();
	}

	private void init() {
		closeButton.setStylePrimaryName("button");
		okButton.setStylePrimaryName("button");
		VerticalPanel verticalPanel = new VerticalPanel();
		add(verticalPanel);

		HorizontalPanel inputPanel = new HorizontalPanel();
		messageLabel = new Label("");
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
				onOkButtonClick(messageCentral, loginData, messageBox.getText());
			}

		});
	}

	@Override
	public void show() {
		messageLabel.setText(getMessageLabelText());
		super.show();
	}

	protected abstract String getMessageLabelText();

	protected abstract void onOkButtonClick(MessageCentral messageCentral, LoginDataDto loginData, String messageBoxText);

}
