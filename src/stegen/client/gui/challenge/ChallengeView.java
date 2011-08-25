package stegen.client.gui.challenge;

import stegen.client.gui.score.*;
import stegen.client.presenter.ChallengePresenter.Display;

import com.google.gwt.cell.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;

public class ChallengeView implements Display {

	private final Column<ScoreTableRow, String> challengeButtonColumn;
	private final ChallengeDialog2 challengeDialog;

	public ChallengeView(Column<ScoreTableRow, String> challengeButtonColumn, ChallengeDialog2 challengeDialog) {
		this.challengeButtonColumn = challengeButtonColumn;
		this.challengeDialog = challengeDialog;
	}

	@Override
	public void addClickOpenChallengeInputHandler(FieldUpdater<ScoreTableRow, String> fieldUpdater) {
		challengeButtonColumn.setFieldUpdater(fieldUpdater);
	}

	@Override
	public void addClickSendChallengeHandler(ClickHandler clickHandler) {
		challengeDialog.addClickChallengeHandler(clickHandler);
	}

	@Override
	public void setupChallengeInputDialog(String challengeeName, String shortInsultText,
			String challengeMessageSubject, String challengeMessage) {
		challengeDialog.setupChallengeInputDialog(challengeeName, shortInsultText, challengeMessageSubject,
				challengeMessage);

	}

	@Override
	public void openChallengeInputDialog() {
		challengeDialog.show();
	}

}
