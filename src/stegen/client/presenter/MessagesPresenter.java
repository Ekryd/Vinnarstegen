package stegen.client.presenter;

import java.util.*;

import stegen.client.event.*;
import stegen.client.gui.message.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.event.dom.client.*;

public class MessagesPresenter implements Presenter {

	private final Display view;
	private final LoginDataDto loginData;
	final ClickHandler clickSendMessageHandler = createClickSendMessageHandler();
	final AbstractAsyncCallback<List<PlayerCommandDto>> eventUpdateSendMessageListCallback = createUpdateSendMessageListCallback();
	final AbstractAsyncCallback<Void> eventCommandSendMessageCallback = createCommandSendMessageCallback();
	final RefreshEventHandler refreshEventHandler = refreshEventHandler();
	final ChangeNicknameEventHandler changeNicknameEventHandler = createChangeNicknameEventHandler();
	private final PlayerCommandServiceAsync playerCommandService;
	private final PlayerServiceAsync playerService;
	private final com.google.gwt.event.shared.EventBus gwtEventBus;

	public interface Display {

		void addSendMessageHandler(ClickHandler clickHandler);

		String getMessageInputContent();
		
		void clearText();

		void changeMessageList(List<MessageTableRow> content);

	}

	public MessagesPresenter(Display messagesView, LoginDataDto loginData,PlayerCommandServiceAsync playerCommandService,PlayerServiceAsync playerService,com.google.gwt.event.shared.EventBus gwtEventBus) {
		this.view = messagesView;
		this.loginData = loginData;
		this.playerCommandService = playerCommandService;
		this.playerService = playerService;
		this.gwtEventBus = gwtEventBus;
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
		gwtEventBus.addHandler(RefreshEvent.TYPE,refreshEventHandler);
		gwtEventBus.addHandler(ChangeNicknameEvent.TYPE, changeNicknameEventHandler);
	}

	private void loadMessages() {
		playerCommandService.getSendMessageCommandStack(10, eventUpdateSendMessageListCallback);
		
	}

	private ClickHandler createClickSendMessageHandler() {
		return new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String messageContent = view.getMessageInputContent();
				boolean emptyMessageContent = messageContent.trim().isEmpty();
				if (!emptyMessageContent) {
					playerService.sendMessage(loginData.player, messageContent, eventCommandSendMessageCallback);
				}
			}
		};
	}

	private AbstractAsyncCallback<Void> createCommandSendMessageCallback() {
		return new AbstractAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				loadMessages();
				view.clearText();
			}
		};
	}

	private AbstractAsyncCallback<List<PlayerCommandDto>> createUpdateSendMessageListCallback() {
		return new AbstractAsyncCallback<List<PlayerCommandDto>>() {

			@Override
			public void onSuccess(List<PlayerCommandDto> result) {
				List<MessageTableRow> content = new ArrayList<MessageTableRow>();
				for (PlayerCommandDto playerCommandDto : result) {
					content.add(new MessageTableRow(playerCommandDto.player.nickname,playerCommandDto.performedDateTime, playerCommandDto.description));
				}
				view.changeMessageList(content);
			}
		};
	}

	private RefreshEventHandler refreshEventHandler(){
		return new RefreshEventHandler() {
			@Override
			public void handleEvent(RefreshEvent event) {
				if (event.getRefreshType() == RefreshType.CHANGES_ON_SERVER_SIDE) {
					loadMessages();
				}
			}
		};
	}
	

	private ChangeNicknameEventHandler createChangeNicknameEventHandler() {
		return new ChangeNicknameEventHandler() {
			@Override
			public void handleEvent(ChangeNicknameEvent event) {
				loadMessages();
			}
		};
	}
}
