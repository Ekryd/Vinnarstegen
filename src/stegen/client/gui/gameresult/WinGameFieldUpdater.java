package stegen.client.gui.gameresult;

import stegen.client.gui.score.*;

import com.google.gwt.cell.client.*;

public abstract class WinGameFieldUpdater {
	public enum ButtonType {
		WIN,
		LOSE;
	}

	public WinGameFieldUpdater() {}

	public FieldUpdater<ScoreTableRow, String> createFieldUpdater(final ButtonType buttonType) {
		return new FieldUpdater<ScoreTableRow, String>() {

			@Override
			public void update(int index, ScoreTableRow row, String value) {
				onButtonClick(row, buttonType);
			}
		};
	}

	public abstract void onButtonClick(ScoreTableRow row, ButtonType buttonType);
}
