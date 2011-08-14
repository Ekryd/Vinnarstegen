package stegen.client.gui.score;

import stegen.client.gui.score.ScoreCellTable2.ScoreCell;

import com.google.gwt.cell.client.*;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.*;
import com.google.gwt.user.cellview.client.*;

public class ButtonColumn2 extends Column<ScoreCell, String> {
	private final String buttonText;

	public ButtonColumn2(String buttonText) {
		super(new ButtonCell());
		this.buttonText = buttonText;
	}

	@Override
	public String getValue(ScoreCell cell) {
		return buttonText;
	}

	@Override
	public void render(Context context, ScoreCell cell, SafeHtmlBuilder sb) {
		dontDisplayButtonsForCurrentUser(context, cell, sb);
	}

	private void dontDisplayButtonsForCurrentUser(Context context, ScoreCell cell, SafeHtmlBuilder sb) {
		if (!cell.isCurrentUser()) {
			super.render(context, cell, sb);
		}
	}
}
