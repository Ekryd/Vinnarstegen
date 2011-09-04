package stegen.client.event;

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
	public void clearCallbacks() {
		callbacks.clear();

	}

	@Override
	public void addHandler(EventCallback<?> callback) {
		callbacks.add(callback);
	}

	@Override
	public void getUserLoginStatus(String hostPageBaseURL) {
		UserLoginStatusCallback callback = callbacks.get(UserLoginStatusCallback.class);
		playerService.getUserLoginStatus(hostPageBaseURL, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void changeNickname(final PlayerDto player, final String nickname) {
		ChangeNicknameCallback callback = callbacks.get(ChangeNicknameCallback.class);
		playerService.changeNickname(player, nickname, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		RegisterPlayerCallback callback = callbacks.get(RegisterPlayerCallback.class);
		playerService.registerPlayer(email, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void sendMessage(PlayerDto player, String message) {
		SendMessageCallback callback = callbacks.get(SendMessageCallback.class);
		playerService.sendMessage(player, message, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void updateSendMessageList() {
		UpdateSendMessageListCallback callback = callbacks.get(UpdateSendMessageListCallback.class);
		playerCommandService.getSendMessageCommandStack(10, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void updatePlayerScoreList() {
		UpdatePlayerScoreListCallback callback = callbacks.get(UpdatePlayerScoreListCallback.class);
		scoreService.getPlayerScoreList(createEmptyCallbackIfNull(callback));
	}

	@Override
	public void clearAllScores(PlayerDto changedBy) {
		ClearScoresCallback callback = callbacks.get(ClearScoresCallback.class);
		scoreService.clearAllScores(changedBy, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void challengePlayer(ChallengeMessageDto message) {
		ChallengeCallback callback = callbacks.get(ChallengeCallback.class);
		scoreService.challengePlayer(message, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void undoPlayerCommand(PlayerDto player) {
		UndoCallback callback = callbacks.get(UndoCallback.class);
		playerCommandService.undoPlayerCommand(player, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto result, PlayerDto changedBy) {
		PlayerWonCallback callback = callbacks.get(PlayerWonCallback.class);
		scoreService.playerWonOverPlayer(winner, loser, result, changedBy, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void refresh() {
		RefreshCallback callback = callbacks.get(RefreshCallback.class);
		createEmptyCallbackIfNull(callback).onSuccess(null);
	}

	@Override
	public void updateGameResultList() {
		UpdateGameResultListCallback callback = callbacks.get(UpdateGameResultListCallback.class);
		playerCommandService.getGameResultCommandStack(10, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void updateUndoCommand() {
		UpdateUndoCommandCallback callback = callbacks.get(UpdateUndoCommandCallback.class);
		playerCommandService.getUndoCommand(createEmptyCallbackIfNull(callback));
	}

	private <T> AsyncCallback<T> createEmptyCallbackIfNull(AsyncCallback<T> callback) {
		if (callback != null) {
			return callback;
		}
		return new EmptyCallback<T>();
	}

}
