package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.message.*;
import stegen.shared.*;

public class MessagesPresenterViewer implements Presenter {

	private final Display view;
	private final EventBus eventBus;
	final UpdateSendMessageListCallback eventUpdateSendMessageListCallback = createUpdateSendMessageListCallback();

	public interface Display {

		String getMessageInputContent();
		
		void changeMessageList(List<MessageTableRow> content);

	}

	public MessagesPresenterViewer(Display messagesView, EventBus eventBus) {
		this.view = messagesView;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initEvents();
		loadMessages();
	}

	private void initEvents() {
		eventBus.addHandler(eventUpdateSendMessageListCallback);
	}

	private void loadMessages() {
		eventBus.updateSendMessageList();
	}


	private UpdateSendMessageListCallback createUpdateSendMessageListCallback() {
		return new UpdateSendMessageListCallback() {

			@Override
			public void onSuccessImpl(List<PlayerCommandDto> result) {
				List<MessageTableRow> content = new ArrayList<MessageTableRow>();
				for (PlayerCommandDto playerCommandDto : result) {
					content.add(new MessageTableRow(playerCommandDto.player.nickname,
							playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeMessageList(content);
			}
		};
	}

}
