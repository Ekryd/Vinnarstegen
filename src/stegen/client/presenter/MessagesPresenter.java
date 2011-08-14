package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.message.MessageTable2.MessageTableContent;
import stegen.client.service.*;
import stegen.client.service.messageprefix.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class MessagesPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto result;
	private final EventBus eventBus;
	private final MessagePrefixGenerator messagePrefixGenerator;
	final ClickHandler clickOpenMessageInputHandler = createClickOpenMessageInputHandler();
	final ClickHandler clickSendMessageHandler = createClickSendMessageHandler();
	final ChangedMessagesCallback eventChangedMessagesCallback = creatEventChangedMessagesCallback();
	final SendMessageCallback eventSendMessageCallback = createEventSendMessageCallback();
	private MessagePrefix currentMessagePrefix;

	public interface Display {

		void setMessageButtonTitle(String buttonText);

		void addClickOpenMessageInputHandler(ClickHandler clickHandler);

		void setMessageInputTitle(String string);

		void addClickSendMessageHandler(ClickHandler clickHandler);

		String getMessageInputContent();

		void changeMessageList(List<MessageTableContent> content);

	}

	public MessagesPresenter(Display messagesView, LoginDataDto result, MessagePrefixGenerator messagePrefixGenerator,
			EventBus eventBus) {
		this.view = messagesView;
		this.result = result;
		this.messagePrefixGenerator = messagePrefixGenerator;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		nextMessagePrefix();
		initView();
		initEvents();
		loadMessages();
	}

	private void nextMessagePrefix() {
		this.currentMessagePrefix = messagePrefixGenerator.getRandomizedPrefix();
	}

	private void initView() {
		view.setMessageButtonTitle(currentMessagePrefix.buttonText);
		view.addClickOpenMessageInputHandler(clickOpenMessageInputHandler);
		view.addClickSendMessageHandler(clickSendMessageHandler);
	}

	private void initEvents() {
		eventBus.addHandler(eventSendMessageCallback);
		eventBus.addHandler(eventChangedMessagesCallback);
	}

	private void loadMessages() {
		eventBus.updateMessageList();
	}

	private ClickHandler createClickOpenMessageInputHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				view.setMessageInputTitle(result.player.nickname + " " + currentMessagePrefix.actionText);
			}
		};
	}

	private ClickHandler createClickSendMessageHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String messageContent = view.getMessageInputContent();
				boolean emptyMessageContent = messageContent.trim().isEmpty();
				if (!emptyMessageContent) {
					String completeMessage = result.player.nickname + " " + currentMessagePrefix.actionText + " "
							+ messageContent;
					eventBus.sendMessage(result.player, completeMessage);
				}

			}
		};
	}

	private SendMessageCallback createEventSendMessageCallback() {
		return new SendMessageCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				eventBus.updateMessageList();
			}
		};
	}

	private ChangedMessagesCallback creatEventChangedMessagesCallback() {
		return new ChangedMessagesCallback() {

			@Override
			public void onSuccessImpl(List<PlayerCommandDto> result) {
				List<MessageTableContent> content = new ArrayList<MessageTableContent>();
				for (PlayerCommandDto playerCommandDto : result) {
					content.add(new MessageTableContentImpl(playerCommandDto.player.nickname,
							playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeMessageList(content);
			}
		};
	}

	private static class MessageTableContentImpl implements MessageTableContent {

		private final String nickname;
		private final Date performedDateTime;
		private final String description;

		public MessageTableContentImpl(String nickname, Date performedDateTime, String description) {
			this.nickname = nickname;
			this.performedDateTime = performedDateTime;
			this.description = description;
		}

		@Override
		public String getPlayerName() {
			return nickname;
		}

		@Override
		public Date getMessageDate() {
			return performedDateTime;
		}

		@Override
		public String getMessage() {
			return description;
		}

	}

}
