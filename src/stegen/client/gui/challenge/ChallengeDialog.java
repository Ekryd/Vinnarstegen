package stegen.client.gui.challenge;

import stegen.client.dto.*;
import stegen.client.gui.common.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.client.ui.*;

public class ChallengeDialog extends DialogBox {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;
	private ChallengeMessage challengeMessage;
	private final Label insultLabel = new Label();
	private final Label subjectLabel = new Label();
	private final TextArea messageBody = new TextArea();
	private final CancelOrOkButtonPanel buttonPanel;

	public ChallengeDialog(MessageCentral messageCentral, LoginDataDto loginData) {
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
				challengeMessage.setMessage(messageBody.getText());
				messageCentral.challenge(challengeMessage.createDto());
			}

			@Override
			protected void onCloseButtonClick() {
				hide();
			}
		};
	}

	private void init() {
		setText("Utmana");
		setAnimationEnabled(true);

		messageBody.setWidth("95%");
		messageBody.setHeight("200px");

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(insultLabel);
		verticalPanel.add(subjectLabel);
		verticalPanel.add(messageBody);
		verticalPanel.add(buttonPanel);
		add(verticalPanel);
	}

	public void setChallengeeAndInitDialog(PlayerDto challengee) {
		challengeMessage = new ChallengeMessage(loginData.player, challengee);
		insultLabel.setText("Du vill kalla " + challengee.nickname + " för " + challengeMessage.getInsult()
				+ " och skicka en utmaning via mail!");
		subjectLabel.setText("Ämne för mailet är " + challengeMessage.getSubject());
		messageBody.setText(challengeMessage.getMessage());
	}
}
