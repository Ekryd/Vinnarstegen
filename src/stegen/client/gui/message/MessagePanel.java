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

	public void addClickOpenMessageInputHandler(ClickHandler clickHandler) {
		messageButton.addClickOpenMessageInputHandler(clickHandler);
	}

	public void setMessageInputTitle(String inputTitle) {
		messageButton.setMessageInputTitle(inputTitle);
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
