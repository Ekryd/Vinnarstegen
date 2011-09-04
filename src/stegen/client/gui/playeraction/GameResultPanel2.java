package stegen.client.gui.playeraction;

import java.util.*;

import com.google.gwt.user.client.ui.*;

public class GameResultPanel2 implements IsWidget {
	private final VerticalPanel baseWidget = new VerticalPanel();
	private final UndoButton2 undoButton = new UndoButton2();
	private final GameResultTable2 table = new GameResultTable2();

	public GameResultPanel2() {
		initLayout();
	}

	private void initLayout() {
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

	public UndoButton2 getUndoButton() {
		return undoButton;
	}

}
