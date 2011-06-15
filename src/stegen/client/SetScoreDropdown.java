package stegen.client;

import stegen.client.dto.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class SetScoreDropdown extends ListBox {
	private final GameResultDto gameResult;
	private final UpdateScoreCallback callback;

	public SetScoreDropdown(GameResultDto gameResult, UpdateScoreCallback callback) {
		super(false);
		this.gameResult = gameResult;
		this.callback = callback;
		addItems();
		addListener();
	}

	private void addListener() {
		addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				SetResult setResult = SetResult.values()[getSelectedIndex()];
				int setIndex = 0;
				for (int i = 0; i < setResult.winnerSets; i++) {
					gameResult.setScores[setIndex++] = new SetScoreDto(11, 1);
				}
				for (int i = 0; i < setResult.loserSets; i++) {
					gameResult.setScores[setIndex++] = new SetScoreDto(1, 11);
				}
				callback.onScoreChange();
			}
		});

	}

	private void addItems() {
		SetResult.values();
		for (SetResult setResult : SetResult.values()) {
			addItem(setResult.description, setResult.name());
		}
	}

	enum SetResult {
		TRE_NOLL(3, 0, "3 - 0"),
		TRE_ETT(3, 1, "3 - 1"),
		TRE_TVA(3, 2, "3 - 2");

		public final int winnerSets;
		public final int loserSets;
		public final String description;

		private SetResult(int winnerScore, int loserScore, String description) {
			this.winnerSets = winnerScore;
			this.loserSets = loserScore;
			this.description = description;
		}
	}

}
