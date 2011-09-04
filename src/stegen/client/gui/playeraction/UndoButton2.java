package stegen.client.gui.playeraction;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class UndoButton2 implements IsWidget {
	private final Button baseWidget = new Button();

	public UndoButton2() {
		initLayout();
	}

	private void initLayout() {
		baseWidget.setStylePrimaryName("button");
		baseWidget.setVisible(false);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void showUndoFailAlert() {
		final DecoratedPopupPanel simplePopup = new DecoratedPopupPanel(true);
		simplePopup.ensureDebugId("cwBasicPopup-simplePopup");
		simplePopup.setWidth("150px");
		simplePopup.setWidget(new HTML(
				"Tyvärr gick det inte att göra undo. <br/>Någon annan har redan gjort något i systemet."));

		int left = baseWidget.getAbsoluteLeft() + 10;
		int top = baseWidget.getAbsoluteTop() + 10;
		simplePopup.setPopupPosition(left, top);

		// Show the popup
		simplePopup.show();
	}

	public void setButtonText(String text) {
		baseWidget.setText(text);
	}

	public void showButton() {
		baseWidget.setVisible(true);
	}

	public void hideButton() {
		baseWidget.setVisible(false);
	}

	public void addClickHandler(ClickHandler handler) {
		baseWidget.addClickHandler(handler);
	}

}
