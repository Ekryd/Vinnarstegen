package stegen.client.gui.dialog;

import stegen.client.dto.*;
import stegen.client.gui.common.*;
import stegen.client.messages.*;

import com.google.gwt.user.client.ui.*;

public abstract class InputDialogBox extends DialogBox {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private final TextBox messageBox = new TextBox();
	private Label messageLabel;
	private final CancelOrOkButtonPanel buttonPanel;

	public InputDialogBox(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		this.buttonPanel = createButtonPanel();
		init();
	}

	private CancelOrOkButtonPanel createButtonPanel() {
		return new CancelOrOkButtonPanel() {

			@Override
			protected void onOkButtonClick() {
				hide();
				onDialogOkButtonClick(messageCentral, loginData, messageBox.getText());
			}

			@Override
			protected void onCloseButtonClick() {
				hide();
			}
		};
	}

	private void init() {
		setAnimationEnabled(true);
		VerticalPanel verticalPanel = new VerticalPanel();
		add(verticalPanel);

		HorizontalPanel inputPanel = new HorizontalPanel();
		messageLabel = new Label("");
		inputPanel.add(messageLabel);

		inputPanel.add(messageBox);
		inputPanel.setCellVerticalAlignment(messageLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(inputPanel);

		verticalPanel.add(buttonPanel);

		setWidget(verticalPanel);

	}

	@Override
	public void show() {
		messageLabel.setText(getMessageLabelText());
		super.show();
	}

	protected abstract String getMessageLabelText();

	protected abstract void onDialogOkButtonClick(MessageCentral messageCentral, LoginDataDto loginData,
			String messageBoxText);

}
