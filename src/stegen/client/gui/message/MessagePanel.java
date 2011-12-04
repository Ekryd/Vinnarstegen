package stegen.client.gui.message;

import java.util.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class MessagePanel implements IsWidget {

	private final VerticalPanel baseWidget = new VerticalPanel();
	private final MessageButton messageButton = new MessageButton();
	private final MessageTable messageTable = new MessageTable();

	public MessagePanel() {
		initLayout();
	}

	private void initLayout() {
		// Stops FireFox from expanding the table
		baseWidget.setHeight("1px");
		baseWidget.add(messageTable);
		baseWidget.add(messageButton);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void setMessageButtonTitle(String buttonTitle) {
		messageButton.setTitle(buttonTitle);
	}

	public void addClickSendMessageHandler(ClickHandler clickHandler) {
		messageButton.addClickSendMessageHandler(clickHandler);
	}

	public String getMessageInputContent() {
		return messageButton.getMessageInputText();
	}

	public void changeMessageList(List<MessageTableRow> content) {
		messageTable.changeContent(content);
	}

}
