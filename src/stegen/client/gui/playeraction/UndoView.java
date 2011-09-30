package stegen.client.gui.playeraction;

import stegen.client.presenter.UndoPresenter.Display;

import com.google.gwt.event.dom.client.*;

public class UndoView implements Display {

	private final UndoButton undoButton;

	public UndoView(UndoButton undoButton) {
		this.undoButton = undoButton;
	}

	@Override
	public void showUndoFailAlert() {
		undoButton.showUndoFailAlert();
	}

	@Override
	public void showUndoButton() {
		undoButton.showButton();
	}

	@Override
	public void hideUndoButton() {
		undoButton.hideButton();
	}

	@Override
	public void addClickUndoHandler(ClickHandler handler) {
		undoButton.addClickHandler(handler);
	}

	@Override
	public void setUndoButtonText(String buttonText) {
		undoButton.setButtonText(buttonText);
	}

}
