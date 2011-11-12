package stegen.client.gui.playeraction;

import java.util.*;

import com.google.gwt.user.client.ui.*;

public class GameResultPanel implements IsWidget {
	private final VerticalPanel baseWidget = new VerticalPanel();
	private final UndoButton undoButton = new UndoButton();
	private final GameResultTable table = new GameResultTable();

	public GameResultPanel() {
		initLayout();
	}

	private void initLayout() {
		// Stops FireFox from expanding the table
		baseWidget.setHeight("1px");
		baseWidget.add(table);
		baseWidget.add(undoButton);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void changeGameResultList(List<GameResultsRow> result) {
		table.changeList(result);
	}

	public UndoButton getUndoButton() {
		return undoButton;
	}

}
