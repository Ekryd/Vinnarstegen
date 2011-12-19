package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.event.callback.*;
import stegen.client.gui.message.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class MessagesPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	private final EventBus eventBus;
	final ClickHandler clickSendMessageHandler = createClickSendMessageHandler();
	final UpdateSendMessageListCallback eventUpdateSendMessageListCallback = createUpdateSendMessageListCallback();
	final CommandSendMessageCallback eventCommandSendMessageCallback = createCommandSendMessageCallback();
	final UpdateRefreshCallback eventCommandRefreshCallback = createCommandRefreshMessagesCallback();
	final CommandChangeNicknameCallback eventCommandChangeNicknameCallback = createCommandChangeNicknameCallback();

	public interface Display {

		void addSendMessageHandler(ClickHandler clickHandler);

		String getMessageInputContent();
		
		void clearText();

		void changeMessageList(List<MessageTableRow> content);

	}

	public MessagesPresenter(Display messagesView, LoginDataDto loginData,EventBus eventBus) {
		this.view = messagesView;
		this.loginData = loginData;
		this.eventBus = eventBus;
	}

	@Override
	public void go() {
		initView();
		initEvents();
		loadMessages();
	}

	private void initView() {
		view.addSendMessageHandler(clickSendMessageHandler);
	}

	private void initEvents() {
		eventBus.addHandler(eventCommandSendMessageCallback);
		eventBus.addHandler(eventUpdateSendMessageListCallback);
		eventBus.addHandler(eventCommandRefreshCallback);
		eventBus.addHandler(eventCommandChangeNicknameCallback);
	}

	private void loadMessages() {
		eventBus.updateSendMessageList();
	}

	private ClickHandler createClickSendMessageHandler() {
		return new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String messageContent = view.getMessageInputContent();
				boolean emptyMessageContent = messageContent.trim().isEmpty();
				if (!emptyMessageContent) {
					eventBus.sendMessage(loginData.player, messageContent);
				}
			}
		};
	}

	private CommandSendMessageCallback createCommandSendMessageCallback() {
		return new CommandSendMessageCallback() {

			@Override
			public void onSuccessImpl(Void result) {
				eventBus.updateSendMessageList();
				view.clearText();
			}
		};
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

	private UpdateRefreshCallback createCommandRefreshMessagesCallback() {
		return new UpdateRefreshCallback() {

			@Override
			public void onSuccessImpl(RefreshType result) {
				if (result == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadMessages();
				}
			}
		};
	}

	private CommandChangeNicknameCallback createCommandChangeNicknameCallback() {
		return new CommandChangeNicknameCallback() {

			@Override
			public void onSuccessImpl(String newNickname) {
				loadMessages();
			}

		};
	}
}
