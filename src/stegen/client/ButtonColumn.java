package stegen.client;

import stegen.client.dto.*;

import com.google.gwt.cell.client.*;
import com.google.gwt.user.cellview.client.*;

public class ButtonColumn extends Column<PlayerScoreDto, String> {
	private final String buttonText;

	public ButtonColumn(String buttonText) {
		super(new ButtonCell());
		this.buttonText = buttonText;
	}

	@Override
	public String getValue(PlayerScoreDto object) {
		return buttonText;
	}

}
