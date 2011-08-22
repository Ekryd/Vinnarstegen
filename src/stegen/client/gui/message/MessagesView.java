package stegen.client.gui.message;

import static stegen.client.gui.BaseHtmlPage.*;

import java.util.*;

import stegen.client.presenter.MessagesPresenter.Display;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class MessagesView implements Display {

	private final VerticalPanel panel = new VerticalPanel();
	private final MessageButton2 messageButton = new MessageButton2();
	private final MessageTable2 messageTable = new MessageTable2();

	public MessagesView() {
		initLayout();
	}

	private void initLayout() {
		panel.add(messageButton);
		panel.add(messageTable);
		MESSAGES_AREA.clearPanel();
		MESSAGES_AREA.addToPanel(panel);
	}

	@Override
	public void setMessageButtonTitle(String buttonTitle) {
		messageButton.setTitle(buttonTitle);
	}

	@Override
	public void addClickOpenMessageInputHandler(ClickHandler clickHandler) {
		messageButton.addClickOpenMessageInputHandler(clickHandler);
	}

	@Override
	public void setMessageInputTitle(String inputTitle) {
		messageButton.setMessageInputTitle(inputTitle);
	}

	@Override
	public void addClickSendMessageHandler(ClickHandler clickHandler) {
		messageButton.addClickSendMessageHandler(clickHandler);
	}

	@Override
	public String getMessageInputContent() {
		return messageButton.getMessageInputText();
	}

	@Override
	public void changeMessageList(List<MessageTableRow> content) {
		messageTable.changeContent(content);
	}

}
