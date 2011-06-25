package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;

public class PlayerCommandPanel extends VerticalPanel implements PlayerCommandListener {
	private final LoginDataDto loginData;
	private final Button undoButton = new Button();
	private final CellTable<PlayerCommandDto> playerCommandTable;
	private final MessageCentral messageCentral;

	public PlayerCommandPanel(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		playerCommandTable = new PlayerCommandTable(messageCentral);
		init();
		messageCentral.listeners.addListener(this);
	}

	private void init() {
		undoButton.setStylePrimaryName("button");

		add(playerCommandTable);

		undoButton.setVisible(false);
		undoButton.addClickHandler(new UndoButtonClickHandler());
		add(undoButton);
	}

	@Override
	public void onUndoCommand(UndoPlayerCommandResult result) {
		if (result == UndoPlayerCommandResult.FAILURE) {
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
			simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
			simplePopup.setWidth("150px");
			simplePopup.setWidget(new HTML(
					"Tyvärr gick det inte att göra undo. <br/>Någon annan har redan ändrat poäng."));

			int left = undoButton.getAbsoluteLeft() + 10;
			int top = undoButton.getAbsoluteTop() + 10;
			simplePopup.setPopupPosition(left, top);

			// Show the popup
			simplePopup.show();
		}

	}

	private void changeButton(PlayerCommandDto result) {
		boolean ownsLastUndoCommand = ownsLastUndoCommand(result);
		if (ownsLastUndoCommand) {
			undoButton.setText("Ångra " + result.description);
		}
		undoButton.setVisible(ownsLastUndoCommand);
	}

	private boolean ownsLastUndoCommand(PlayerCommandDto result) {
		return result != null && result.player.email.equals(loginData.player.email);
	}

	private final class UndoButtonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			messageCentral.undo(loginData.player);
		}
	}

	@Override
	public void onUndoCommandUpdate(PlayerCommandDto result) {
		changeButton(result);
	}

	@Override
	public void onPlayerCommandListUpdate(List<PlayerCommandDto> result) {}

}
