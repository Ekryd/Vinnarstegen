package stegen.client.event;

import java.util.*;

import stegen.client.event.callback.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public class EventBusImpl implements EventBus {

	private final PlayerCommandServiceAsync playerCommandService;
	private final ScoreServiceAsync scoreService;
	private final PlayerServiceAsync playerService;
	private final CallbackRepository callbacks;

	public static EventBus create(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		return new EventBusImpl(playerCommandService, scoreService, playerService, new CallbackRepositoryImpl());
	}

	public static EventBus createForTest(PlayerCommandServiceAsync playerCommandService,
			ScoreServiceAsync scoreService, PlayerServiceAsync playerService, CallbackRepository callbacks) {
		return new EventBusImpl(playerCommandService, scoreService, playerService, callbacks);
	}

	private EventBusImpl(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService, CallbackRepository callbacks) {
		this.playerCommandService = playerCommandService;
		this.scoreService = scoreService;
		this.playerService = playerService;
		this.callbacks = callbacks;
	}

	@Override
	public void addHandler(EventCallback<?> callback) {
		callbacks.add(callback);
	}

	@Override
	public void checkUserLoginStatus(String hostPageBaseURL) {
		@SuppressWarnings("unchecked")
		AsyncCallback<LoginDataDto> callback = callbacks.get(CheckUserLoginStatusCallback.class);
		playerService.userLoginStatus(hostPageBaseURL, callback);
	}

	@Override
	public void changeNickname(final PlayerDto player, final String nickname) {
		@SuppressWarnings("unchecked")
		AsyncCallback<PlayerDto> callback = callbacks.get(ChangeNicknameCallback.class);
		playerService.changeNickname(player, nickname, callback);
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		@SuppressWarnings("unchecked")
		AsyncCallback<Void> callback = callbacks.get(RegisterPlayerCallback.class);
		playerService.registerPlayer(email, callback);
	}

	@Override
	public void sendMessage(PlayerDto player, String message) {
		@SuppressWarnings("unchecked")
		AsyncCallback<Void> callback = callbacks.get(SendMessageCallback.class);
		playerService.sendMessage(player, message, callback);
	}

	@Override
	public void updateMessageList() {
		@SuppressWarnings("unchecked")
		AsyncCallback<List<PlayerCommandDto>> callback = callbacks.get(ChangedMessagesCallback.class);
		playerCommandService.getSendMessageCommandStack(10, callback);
	}

	@Override
	public void updateScoreList() {
		@SuppressWarnings("unchecked")
		AsyncCallback<List<PlayerScoreDto>> callback = callbacks.get(ChangedScoresCallback.class);
		scoreService.getPlayerList(callback);
	}

}
