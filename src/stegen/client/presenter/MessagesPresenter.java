package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.message.*;
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
	final UpdateSendMessageListCallback eventChangedMessagesCallback = creatEventChangedMessagesCallback();
	final SendMessageCallback eventSendMessageCallback = createEventSendMessageCallback();
	final RefreshCallback eventRefreshCallback = createRefreshMessagesCallback();
	private MessagePrefix currentMessagePrefix;

	public interface Display {

		void setMessageButtonTitle(String buttonText);

		void addClickOpenMessageInputHandler(ClickHandler clickHandler);

		void setMessageInputTitle(String string);

		void addClickSendMessageHandler(ClickHandler clickHandler);

		String getMessageInputContent();

		void changeMessageList(List<MessageTableRow> content);

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
		changeMessagePrefixOnButton();
		initView();
		initEvents();
		loadMessages();
	}

	private void changeMessagePrefixOnButton() {
		this.currentMessagePrefix = messagePrefixGenerator.getRandomizedPrefix();
		view.setMessageButtonTitle(currentMessagePrefix.buttonText);
	}

	private void initView() {
		view.addClickOpenMessageInputHandler(clickOpenMessageInputHandler);
		view.addClickSendMessageHandler(clickSendMessageHandler);
	}

	private void initEvents() {
		eventBus.addHandler(eventSendMessageCallback);
		eventBus.addHandler(eventChangedMessagesCallback);
		eventBus.addHandler(eventRefreshCallback);
	}

	private void loadMessages() {
		eventBus.updateSendMessageList();
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
				eventBus.updateSendMessageList();
				changeMessagePrefixOnButton();
			}
		};
	}

	private UpdateSendMessageListCallback creatEventChangedMessagesCallback() {
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

	private RefreshCallback createRefreshMessagesCallback() {
		return new RefreshCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				eventBus.updateSendMessageList();
				changeMessagePrefixOnButton();
			}
		};
	}

}
