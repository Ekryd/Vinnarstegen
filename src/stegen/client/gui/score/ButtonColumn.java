package stegen.client.gui.score;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.safehtml.shared.*;
import com.google.gwt.user.cellview.client.*;

public class ButtonColumn extends Column<PlayerScoreDto, String> {
	private final String buttonText;
	private final LoginDataDto loginData;

	public ButtonColumn(LoginDataDto loginData, String buttonText) {
		super(new ButtonCell());
		this.loginData = loginData;
		this.buttonText = buttonText;
	}

	@Override
	public String getValue(PlayerScoreDto object) {
		return buttonText;
	}

	@Override
	public void render(Context context, PlayerScoreDto object, SafeHtmlBuilder sb) {
		dontDisplayButtonsForCurrentUser(context, object, sb);
	}

	private void dontDisplayButtonsForCurrentUser(Context context, PlayerScoreDto object, SafeHtmlBuilder sb) {
		if (!loginData.player.email.equals(object.player.email)) {
			super.render(context, object, sb);
		}
	}
}
