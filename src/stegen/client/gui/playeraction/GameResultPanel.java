package stegen.client.gui.playeraction;

import stegen.client.dto.*;
import stegen.client.messages.*;
import stegen.shared.*;

import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.ui.*;

public class GameResultPanel extends VerticalPanel {
	private final Button undoButton;
	private final CellTable<PlayerCommandDto> table;

	public GameResultPanel(MessageCentral messageCentral, LoginDataDto loginData) {
		table = new GameResultTable(messageCentral);
		undoButton = new UndoButton(messageCentral, loginData);
		init();
	}

	private void init() {
		add(table);
		add(undoButton);
	}

}
