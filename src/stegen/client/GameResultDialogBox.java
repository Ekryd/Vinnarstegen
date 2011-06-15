package stegen.client;

import stegen.client.dto.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public abstract class GameResultDialogBox extends DialogBox {
	private final Button closeButton = new Button("Avbryt");
	private final Button okButton = new Button("Ok");
	private final EmailAddressDto winnerEmail;
	private final EmailAddressDto loserEmail;
	private final GameResultDto gameResult = GameResultDto.createEmptyGameResult();
	private final Label scoreLabel = new Label();

	public GameResultDialogBox(EmailAddressDto winnerEmail, EmailAddressDto loserEmail) {
		this.winnerEmail = winnerEmail;
		this.loserEmail = loserEmail;
		setupComponents();
		setupButtonHandler();
	}

	private void setupComponents() {
		setText("Matchresultat");
		setAnimationEnabled(true);

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new HTML("<p>Skriv in matchresultatet. Vinnaren står först</p>"));
		Widget gameSetResultTable = GameSetResultTable.createTableWithData(new UpdateScoreCallback() {

			@Override
			public void onScoreChange() {
				updateScoreLabel();
			}
		}, winnerEmail, loserEmail, gameResult);
		verticalPanel.add(gameSetResultTable);

		// CellTable<String> rubrikTable = new CellTable<String>();
		// rubrikTable.addColumn(new TextColumn<String>() {
		//
		// @Override
		// public String getValue(String object) {
		// return null;
		// }
		// }, winnerEmail.address);
		// rubrikTable.addColumn(new TextColumn<String>() {
		//
		// @Override
		// public String getValue(String object) {
		// return null;
		// }
		// }, loserEmail.address);
		// new ListDataProvider<String>().addDataDisplay(rubrikTable);
		// verticalPanel.add(rubrikTable);
		//
		// ListBox setList = new SetScoreDropdown(gameResult, new
		// UpdateScoreCallback() {
		//
		// @Override
		// public void onScoreChange() {
		// // TODO Auto-generated method stub
		//
		// }
		// });
		// verticalPanel.add(setList);

		updateScoreLabel();
		verticalPanel.add(scoreLabel);

		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.setWidth("100%");
		buttonPanel.add(okButton);
		buttonPanel.add(closeButton);
		buttonPanel.setCellHorizontalAlignment(okButton, HasHorizontalAlignment.ALIGN_LEFT);
		buttonPanel.setCellHorizontalAlignment(closeButton, HasHorizontalAlignment.ALIGN_RIGHT);
		verticalPanel.add(buttonPanel);
		setWidget(verticalPanel);
	}

	private void updateScoreLabel() {
		GameResultCalculator calculator = new GameResultCalculator(gameResult);
		int win = calculator.getWonSets();
		int lose = calculator.getLostSets();
		scoreLabel.setText(win + " - " + lose);
		if (lose >= win) {
			okButton.setEnabled(false);
			scoreLabel.setStylePrimaryName("score_big_error");
		} else {
			okButton.setEnabled(true);
			scoreLabel.setStylePrimaryName("score_big");
		}
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
				sendGameResult(gameResult);
			}

		});
	}

	protected abstract void sendGameResult(GameResultDto gameResult);
}
