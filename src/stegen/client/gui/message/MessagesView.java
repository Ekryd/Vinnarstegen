package stegen.client.gui.message;

import java.util.*;

import stegen.client.presenter.MessagesPresenter.Display;

import com.google.gwt.event.dom.client.*;

public class MessagesView implements Display {

	private final MessagePanel messagePanel;

	public MessagesView(MessagePanel messagePanel) {
		this.messagePanel = messagePanel;
	}

	@Override
	public void setMessageButtonTitle(String buttonTitle) {
		messagePanel.setMessageButtonTitle(buttonTitle);
	}

	@Override
	public void addClickSendMessageHandler(ClickHandler clickHandler) {
		messagePanel.addClickSendMessageHandler(clickHandler);
	}

	@Override
	public String getMessageInputContent() {
		return messagePanel.getMessageInputContent();
	}

	@Override
	public void changeMessageList(List<MessageTableRow> content) {
		messagePanel.changeMessageList(content);
	}

}
