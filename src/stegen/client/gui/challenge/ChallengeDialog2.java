package stegen.client.gui.challenge;

import stegen.client.gui.common.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class ChallengeDialog2 implements IsWidget {

	private final DialogBox baseWidget = new DialogBox();
	private final Label insultLabel = new Label();
	private final Label subjectLabel = new Label();
	private final TextArea messageBody = new TextArea();
	private final CancelOrOkButtonPanel2 buttonPanel = new CancelOrOkButtonPanel2();

	public ChallengeDialog2() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setText("Utmana");
		baseWidget.setAnimationEnabled(true);

		messageBody.setWidth("95%");
		messageBody.setHeight("200px");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(insultLabel);
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

	public void setChallengeeAndInitDialog(String challengeeName, String shortInsultText,
			String challengeMessageSubject, String challengeMessage) {
		insultLabel.setText("Du vill kalla " + challengeeName + " för " + shortInsultText
				+ " och skicka en utmaning via mail!");
		subjectLabel.setText("Ämne för mailet är " + challengeMessageSubject);
		messageBody.setText(challengeMessage);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}
}
