package stegen.client.gui.message;

import stegen.client.gui.dialog.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class MessageButton implements IsWidget {
	private final Button baseWidget = new Button();
	private InputDialogBox messageDialog = new InputDialogBox();

	public MessageButton() {
		initLayout();
		addHandler();
	}

	private void initLayout() {
		baseWidget.setStylePrimaryName("button");
	}

	private void addHandler() {
		baseWidget.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int left = baseWidget.getAbsoluteLeft() + 10;
				int top = baseWidget.getAbsoluteTop() + 10;
				messageDialog.resetInputText();
				messageDialog.setPopupPosition(left, top);

				messageDialog.showDialog();
			}
		});
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setTitle(String buttonTitle) {
		baseWidget.setText(buttonTitle);
	}

	public void addClickSendMessageHandler(ClickHandler clickHandler) {
		messageDialog.addClickOkHandler(clickHandler);
	}

	public String getMessageInputText() {
		return messageDialog.getInputText();
	}

}
