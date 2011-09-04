package stegen.client.gui.playeraction;

import java.util.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.*;
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

	public void showUndoFailAlert() {
		undoButton.showUndoFailAlert();
	}

	public void setUndoButtonText(String text) {
		undoButton.setButtonText(text);
	}

	public void showUndoButton() {
		undoButton.showButton();
	}

	public void hideUndoButton() {
		undoButton.hideButton();
	}

	public HandlerRegistration addClickUndoHandler(ClickHandler handler) {
		return undoButton.addClickHandler(handler);
	}

	public void changeGameResultList(List<GameResultsRow> result) {
		table.changeList(result);
	}

}
