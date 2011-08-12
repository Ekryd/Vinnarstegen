package stegen.client.gui.playeraction;

import java.util.*;

import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class UndoButton extends Button implements PlayerCommandListener {

	private final MessageCentral messageCentral;
	private final LoginDataDto loginData;

	public UndoButton(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
		messageCentral.listeners.addListener(this);
	}

	private void init() {
		setStylePrimaryName("button");
		setVisible(false);
		addClickHandler(new UndoButtonClickHandler());
	}

	@Override
	public void onUndoCommand(UndoPlayerCommandResult result) {
		if (result == UndoPlayerCommandResult.FAILURE) {
			final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
			simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
			simplePopup.setWidth("150px");
			simplePopup.setWidget(new HTML(
					"Tyvärr gick det inte att göra undo. <br/>Någon annan har redan ändrat poäng."));

			int left = getAbsoluteLeft() + 10;
			int top = getAbsoluteTop() + 10;
			simplePopup.setPopupPosition(left, top);

			// Show the popup
			simplePopup.show();
		}

	}

	private void changeButton(PlayerCommandDto result) {
		boolean ownsLastUndoCommand = ownsLastUndoCommand(result);
		if (ownsLastUndoCommand) {
			setText("Ångra " + result.description);
		}
		setVisible(ownsLastUndoCommand);
	}

	private boolean ownsLastUndoCommand(PlayerCommandDto result) {
		return result != null && result.player.email.equals(loginData.player.email);
	}

	@Override
	public void onUndoCommandUpdate(PlayerCommandDto result) {
		changeButton(result);
	}

	@Override
	public void onPlayerMiscCommandListUpdate(List<PlayerCommandDto> result) {}

	private final class UndoButtonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			messageCentral.undo(loginData.player);
		}
	}

	@Override
	public void onGameResultListUpdate(List<PlayerCommandDto> result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onLoginStatusListUpdate(List<PlayerCommandDto> result) {
		// TODO Auto-generated method stub

	}

}
