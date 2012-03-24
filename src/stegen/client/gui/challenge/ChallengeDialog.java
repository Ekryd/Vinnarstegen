package stegen.client.gui.challenge;

import stegen.client.gui.common.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class ChallengeDialog implements IsWidget {

	private final DialogBox baseWidget = new DialogBox();
	private final Label textLabel = new Label();
	private final Label subjectLabel = new Label();
	private final TextArea messageBody = new TextArea();
	private final CancelOrOkButtonPanel buttonPanel = new CancelOrOkButtonPanel();

	public ChallengeDialog() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setText("Utmana");
		baseWidget.setAnimationEnabled(true);

		messageBody.setWidth("95%");
		messageBody.setHeight("200px");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(textLabel);
		verticalPanel.add(subjectLabel);
		verticalPanel.add(messageBody);
		verticalPanel.add(buttonPanel);
		baseWidget.add(verticalPanel);

		HideDialogHandler hideDialogHandler = new HideDialogHandler(baseWidget);
		buttonPanel.addClickOkHandler(hideDialogHandler);
		buttonPanel.addClickCloseHandler(hideDialogHandler);
	}

	public void addClickChallengeHandler(ClickHandler clickHandler) {
		buttonPanel.addClickOkHandler(clickHandler);
	}

	public void setupChallengeInputDialog(String challengeeName,
			String challengeMessageSubject, String challengeMessage) {
		textLabel.setText("Du vill utmana " + challengeeName + " och skicka en utmaning via mail!");
		subjectLabel.setText("Ämne för mailet är '" + challengeMessageSubject+"'");
		messageBody.setText(challengeMessage);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void show() {
		baseWidget.center();
	}

	public String getUserModifiedMessage() {
		return messageBody.getText();
	}
}
