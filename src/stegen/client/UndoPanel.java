package stegen.client;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.messages.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.view.client.*;

public class UndoPanel extends VerticalPanel implements UndoListener {
	private final LoginDataDto loginData;
	private final Button undoButton = new Button();
	private final CellTable<PlayerCommandDto> undoList = new CellTable<PlayerCommandDto>();
	private final ListDataProvider<PlayerCommandDto> undoListDataProvider = new ListDataProvider<PlayerCommandDto>();
	private final MessageCentral messageCentral;

	public UndoPanel(MessageCentral messageCentral, LoginDataDto loginData) {
		this.messageCentral = messageCentral;
		this.loginData = loginData;
		init();
		messageCentral.addListener(this);
	}

	private void init() {
		undoList.addColumn(new TextColumn<PlayerCommandDto>() {

			@Override
			public String getValue(PlayerCommandDto object) {
				return object.player.address;
			}
		}, "Utfört av");
		undoList.addColumn(new TextColumn<PlayerCommandDto>() {

			@Override
			public String getValue(PlayerCommandDto object) {
				return object.description;
			}
		}, "Senaste händelse");
		add(undoList);

		undoListDataProvider.addDataDisplay(undoList);

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

	private void changeList(List<PlayerCommandDto> result) {
		undoListDataProvider.setList(result);
	}

	private boolean ownsLastUndoCommand(PlayerCommandDto result) {
		return result != null && result.player.address.equals(loginData.emailAddress.address);
	}

	private final class UndoButtonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			messageCentral.undo(loginData.emailAddress);
		}
	}

	@Override
	public void onUndoListUpdate(List<PlayerCommandDto> result) {
		changeList(result);
	}

	@Override
	public void onUndoCommandUpdate(PlayerCommandDto result) {
		changeButton(result);
	}

}
