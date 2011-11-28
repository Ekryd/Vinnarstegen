package stegen.client.event;

import stegen.client.event.callback.*;
import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

/**
 * @author Björn Ekryd and Askia Linder
 */
public class EventBusImpl implements EventBus {

	private final PlayerCommandServiceAsync playerCommandService;
	private final ScoreServiceAsync scoreService;
	private final PlayerServiceAsync playerService;
	private final RefreshService refreshService;
	private final CallbackRepository callbacks;

	public static EventBus create(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService) {
		return new EventBusImpl(playerCommandService, scoreService, playerService, new CallbackRepositoryImpl());
	}

	private EventBusImpl(PlayerCommandServiceAsync playerCommandService, ScoreServiceAsync scoreService,
			PlayerServiceAsync playerService, CallbackRepository callbacks) {
		this.playerCommandService = playerCommandService;
		this.scoreService = scoreService;
		this.playerService = playerService;
		this.callbacks = callbacks;
		this.refreshService = new RefreshService(playerCommandService);
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
		UpdateLoginStatusCallback callback = callbacks.get(UpdateLoginStatusCallback.class);
		playerService.getUserLoginStatus(hostPageBaseURL, createEmptyCallbackIfNull(callback));
	}
	
	

	@Override
	public void changeNickname(final PlayerDto player, final String nickname) {
		CommandChangeNicknameCallback callbackCommand = callbacks.get(CommandChangeNicknameCallback.class);
		playerService.changeNickname(player, nickname, createEmptyCallbackIfNull(callbackCommand));
	}

	@Override
	public void registerPlayer(EmailAddressDto email) {
		CommandRegisterPlayerCallback callbackCommand = callbacks.get(CommandRegisterPlayerCallback.class);
		playerService.registerPlayer(email, createEmptyCallbackIfNull(callbackCommand));
	}
	
	@Override
	public void isNewUserPasswordOk(String newUserPassword) {
		UpdateIsNewUserPasswordOkCallback callbackCommand = callbacks.get(UpdateIsNewUserPasswordOkCallback.class);
		playerService.isNewUserPasswordOk(newUserPassword, createEmptyCallbackIfNull(callbackCommand));		
	}

	@Override
	public void sendMessage(PlayerDto player, String message) {
		CommandSendMessageCallback callbackCommand = callbacks.get(CommandSendMessageCallback.class);
		playerService.sendMessage(player, message, createEmptyCallbackIfNull(callbackCommand));
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
		CommandClearScoresCallback callbackCommand = callbacks.get(CommandClearScoresCallback.class);
		scoreService.clearAllScores(changedBy, createEmptyCallbackIfNull(callbackCommand));
	}

	@Override
	public void challengePlayer(ChallengeMessageDto message) {
		CommandChallengeCallback callbackCommand = callbacks.get(CommandChallengeCallback.class);
		scoreService.challengePlayer(message, createEmptyCallbackIfNull(callbackCommand));
	}

	@Override
	public void undoPlayerCommand(PlayerDto player) {
		CommandUndoCallback callbackCommand = callbacks.get(CommandUndoCallback.class);
		playerCommandService.undoPlayerCommand(player, createEmptyCallbackIfNull(callbackCommand));
	}

	@Override
	public void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto result, PlayerDto changedBy) {
		CommandPlayerWonCallback callbackCommand = callbacks.get(CommandPlayerWonCallback.class);
		scoreService.playerWonOverPlayer(winner, loser, result, changedBy, createEmptyCallbackIfNull(callbackCommand));
	}

	@Override
	public void refresh() {
		UpdateRefreshCallback callback = callbacks.get(UpdateRefreshCallback.class);
		refreshService.refresh(createEmptyCallbackIfNull(callback));
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

	@Override
	public void updatePlayerMiscCommandList() {
		UpdatePlayerMiscCommandListCallback callback = callbacks.get(UpdatePlayerMiscCommandListCallback.class);
		playerCommandService.getMiscPlayerCommandStack(10, createEmptyCallbackIfNull(callback));
	}

	@Override
	public void updateLoginStatusList() {
		UpdateLoginStatusListCallback callback = callbacks.get(UpdateLoginStatusListCallback.class);
		playerCommandService.getLoginStatusCommandStack(10, createEmptyCallbackIfNull(callback));
	}

	private <T> AsyncCallback<T> createEmptyCallbackIfNull(AsyncCallback<T> callback) {
		if (callback != null) {
			return callback;
		}
		return new EmptyCallback<T>();
	}

	

	
}
