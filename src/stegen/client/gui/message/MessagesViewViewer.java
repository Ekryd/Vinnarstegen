package stegen.client.gui.message;

import java.util.*;

import stegen.client.presenter.MessagesPresenterViewer.Display;

public class MessagesViewViewer implements Display {

	private final MessagePanel messagePanel;

	public MessagesViewViewer(MessagePanel messagePanel) {
		this.messagePanel = messagePanel;
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
