package stegen.client.gui.gameresult;

import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class SetScoreDropdown extends VerticalPanel {
	private final GameResultDto gameResult;
	private final UpdateScoreCallback scoreCallback;
	private final ListBox listBox = new ListBox(false);
	private Label playersLabel;

	public SetScoreDropdown(GameResultDto gameResult, UpdateScoreCallback scoreCallback) {
		this.gameResult = gameResult;
		this.scoreCallback = scoreCallback;
		addItems();
		addListener();
	}

	private void addListener() {
		listBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				SetResult setResult = SetResult.values()[listBox.getSelectedIndex()];
				int setIndex = 0;
				for (int i = 0; i < setResult.winnerSets; i++) {
					gameResult.setScores[setIndex++] = new SetScoreDto(11, 1);
				}
				for (int i = 0; i < setResult.loserSets; i++) {
					gameResult.setScores[setIndex++] = new SetScoreDto(1, 11);
				}
				for (int i = setIndex; i < gameResult.setScores.length; i++) {
					gameResult.setScores[setIndex++] = new SetScoreDto(0, 0);
				}
				scoreCallback.onScoreChange();
			}
		});

	}

	private void addItems() {
		playersLabel = new Label();
		playersLabel.setStylePrimaryName("players_label");
		add(playersLabel);

		setWidth("100%");
		for (SetResult setResult : SetResult.values()) {
			listBox.addItem(setResult.description, setResult.name());
		}
		listBox.setStylePrimaryName("center");
		add(listBox);
		setCellHorizontalAlignment(listBox, ALIGN_CENTER);
	}

	public void setPlayers(PlayerDto winner, PlayerDto loser) {
		playersLabel.setText(winner.nickname + " - " + loser.nickname);
	}

}
