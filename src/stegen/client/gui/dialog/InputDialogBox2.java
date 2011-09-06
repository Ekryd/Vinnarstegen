package stegen.client.gui.dialog;

import stegen.client.gui.common.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class InputDialogBox2 implements IsWidget {

	private final DialogBox baseWidget = new DialogBox();
	private final TextBox messageBox = new TextBox();
	private final Label messageLabel = new Label("");
	private final CancelOrOkButtonPanel2 buttonPanel = new CancelOrOkButtonPanel2();

	public InputDialogBox2() {
		createButtonEvents();
		initLayout();
	}

	private void createButtonEvents() {
		ClickHandler hideDialogAction = new HideDialogHandler(baseWidget);
		buttonPanel.addClickCloseHandler(hideDialogAction);
		buttonPanel.addClickOkHandler(hideDialogAction);
	}

	private void initLayout() {
		baseWidget.setAnimationEnabled(true);
		VerticalPanel verticalPanel = new VerticalPanel();
		baseWidget.add(verticalPanel);

		HorizontalPanel inputPanel = new HorizontalPanel();
		inputPanel.add(messageLabel);

		inputPanel.add(messageBox);
		inputPanel.setCellVerticalAlignment(messageLabel, HasVerticalAlignment.ALIGN_MIDDLE);
		verticalPanel.add(inputPanel);

		verticalPanel.add(buttonPanel);

		baseWidget.setWidget(verticalPanel);

	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setPopupPosition(int left, int top) {
		baseWidget.setPopupPosition(left, top);
	}

	public void showDialog() {
		baseWidget.show();
	}

	public void setLabelText(String text) {
		messageLabel.setText(text);
	}

	public String getInputText() {
		return messageBox.getText();
	}

	public void addClickOkHandler(ClickHandler clickHandler) {
		buttonPanel.addClickOkHandler(clickHandler);
	}

	public void resetInputText() {
		messageBox.setText("");
	}

}
