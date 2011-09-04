package stegen.client.gui.gameresult;

import stegen.client.gui.common.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class WinGameDialogBox2 implements IsWidget {
	private final DialogBox baseWidget = new DialogBox();
	private final Label scoreLabel = new Label();
	private final CancelOrOkButtonPanel2 buttonPanel = new CancelOrOkButtonPanel2();
	private final SetScoreDropdown2 setScoreDropdown = new SetScoreDropdown2();

	public WinGameDialogBox2() {
		initLayout();
		initSetScoreDropdownEvents();
	}

	private void initLayout() {
		baseWidget.setText("Matchresultat");
		baseWidget.setAnimationEnabled(true);

		VerticalPanel verticalPanel = new VerticalPanel();
		baseWidget.setWidget(verticalPanel);

		verticalPanel.add(new HTML("<p>Skriv in matchresultatet. Vinnaren står först</p>"));
		verticalPanel.add(setScoreDropdown);
		verticalPanel.add(scoreLabel);
		verticalPanel.add(buttonPanel);

		HideDialogHandler hideDialogHandler = new HideDialogHandler(baseWidget);
		buttonPanel.addClickOkHandler(hideDialogHandler);
		buttonPanel.addClickCloseHandler(hideDialogHandler);

		updateScoreLabel();
	}

	private void initSetScoreDropdownEvents() {
		ChangeHandler onScoreChangeEventHandler = new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				updateScoreLabel();
			}
		};
		setScoreDropdown.addOnScoreChangeEventHandler(onScoreChangeEventHandler);

	}

	private void updateScoreLabel() {
		SetResult setResult = setScoreDropdown.getSetResult();
		int win = setResult.winnerSets;
		int lose = setResult.loserSets;
		scoreLabel.setText(win + " - " + lose);
		if (lose >= win) {
			buttonPanel.setOkButtonEnabled(false);
			scoreLabel.setStylePrimaryName("score_big_error");
		} else {
			buttonPanel.setOkButtonEnabled(true);
			scoreLabel.setStylePrimaryName("score_big");
		}
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setupWinGameInputDialog(String winnerName, String loserName) {
		setScoreDropdown.setPlayers(winnerName, loserName);
		setScoreDropdown.resetScoreToZeroZero();
		updateScoreLabel();
	}

	public SetResult getSetResult() {
		return setScoreDropdown.getSetResult();
	}

	public void show() {
		baseWidget.center();
	}

	public void addClickWinGameHandler(ClickHandler clickHandler) {
		buttonPanel.addClickOkHandler(clickHandler);
	}

}
