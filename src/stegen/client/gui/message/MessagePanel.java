package stegen.client.gui.message;

import java.util.*;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

public class MessagePanel implements IsWidget {

	private final VerticalPanel baseWidget = new VerticalPanel();
	private final HorizontalPanel textPanel = new HorizontalPanel();
	private final Button messageButton = new Button();
	private final TextArea messageBox = new TextArea();
	private final MessageTable messageTable = new MessageTable();

	public MessagePanel() {
		initLayout();
	}

	private void initLayout() {
		// Stops FireFox from expanding the table
		baseWidget.setHeight("1px");
		baseWidget.add(messageTable);
		baseWidget.add(textPanel);
		textPanel.add(messageBox);
		textPanel.add(messageButton);
		messageButton.setText("Skicka meddelande");
		messageButton.setStylePrimaryName("button");
		messageBox.setCharacterWidth(100);
	}

	@Override
	public Widget asWidget() {
		return baseWidget;
	}

	public void addSendMessageHandler(ClickHandler clickHandler) {
		messageButton.addClickHandler(clickHandler);
	}

	
	public String getMessageInputContent() {
		return messageBox.getValue();
	}

	public void changeMessageList(List<MessageTableRow> content) {
		messageTable.changeContent(content);
	}
	
	public void resetInputText() {
		messageBox.setText("");
	}

}
