package stegen.client.gui.score;

import com.google.gwt.cell.client.*;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.*;
import com.google.gwt.user.cellview.client.*;

public class ButtonColumn extends Column<ScoreTableRow, String> {
	private final String buttonText;

	public ButtonColumn(String buttonText) {
		super(new ButtonCell());
		this.buttonText = buttonText;
	}

	@Override
	public String getValue(ScoreTableRow cell) {
		return buttonText;
	}

	@Override
	public final void render(Context context, ScoreTableRow cell, SafeHtmlBuilder sb) {
		if (showButton(cell)) {
			super.render(context, cell, sb);
		}
	}

	protected boolean showButton(ScoreTableRow cell) {
		boolean dontDisplayForCurrentUser = !cell.currentUser;
		return dontDisplayForCurrentUser;
	}
}
