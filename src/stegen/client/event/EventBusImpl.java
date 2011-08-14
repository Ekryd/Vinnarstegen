package stegen.client.event;

import stegen.client.event.callback.*;
import stegen.client.service.*;
import stegen.shared.*;

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
	public void getUserLoginStatus(String hostPageBaseURL) {
		UserLoginStatusCallback callback = callbacks.get(UserLoginStatusCallback.class);
		playerService.getUserLoginStatus(hostPageBaseURL, callback);
	}

	@Override
	public void changeNickname(final PlayerDto player, final String nickname) {
		ChangeNicknameCallback callback = callbacks.get(ChangeNicknameCallback.class);
		playerService.changeNickname(player, nickname, callback);
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		RegisterPlayerCallback callback = callbacks.get(RegisterPlayerCallback.class);
		playerService.registerPlayer(email, callback);
	}

	@Override
	public void sendMessage(PlayerDto player, String message) {
		SendMessageCallback callback = callbacks.get(SendMessageCallback.class);
		playerService.sendMessage(player, message, callback);
	}

	@Override
	public void updateSendMessageList() {
		UpdateSendMessageListCallback callback = callbacks.get(UpdateSendMessageListCallback.class);
		playerCommandService.getSendMessageCommandStack(10, callback);
	}

	@Override
	public void updatePlayerScoreList() {
		UpdatePlayerScoreListCallback callback = callbacks.get(UpdatePlayerScoreListCallback.class);
		scoreService.getPlayerScoreList(callback);
	}

	@Override
	public void clearAllScores(PlayerDto changedBy) {
		ClearScoresCallback callback = callbacks.get(ClearScoresCallback.class);
		scoreService.clearAllScores(changedBy, callback);
	}

}
