package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;

public class MessageCentral {
	private final PlayerCommandServiceAsync playerCommandService = GWT.create(PlayerCommandService.class);
	private final ScoreServiceAsync scoreService = GWT.create(ScoreService.class);
	private final PlayerServiceAsync playerService = GWT.create(PlayerService.class);
	public final ListenerLists listeners = new ListenerLists();

	public void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto gameResult, PlayerDto changedBy) {
		scoreService.playerWonOverPlayer(winner, loser, gameResult, changedBy, new DefaultCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				updatePlayerCommandList();
				updateScores();
				updateUndoCommand();
			}
		});
	}

	public void clearAllScores(PlayerDto changedBy) {
		scoreService.clearAllScores(changedBy, new DefaultCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				updatePlayerCommandList();
				updateScores();
				updateUndoCommand();
			}
		});
	}

	public void undo(final PlayerDto player) {
		playerCommandService.undoPlayerCommand(player, new DefaultCallback<UndoPlayerCommandResult>() {

			@Override
			public void onSuccess(UndoPlayerCommandResult result) {
				listeners.onUndoCommand(result);
				updatePlayerCommandList();
				updateScores();
				updateUndoCommand();
			}

		});
	}

	public void sendMessage(PlayerDto player, String message) {
		playerService.sendMessage(player, message, new DefaultCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				updateMessageList();
				updatePlayerCommandList();
			}

		});
	}

	public void userLoginStatus(DefaultCallback<LoginDataDto> defaultCallback) {
		playerService.userLoginStatus(GWT.getHostPageBaseURL(), defaultCallback);
	}

	public void changeNickname(final PlayerDto player, final String nickname) {
		playerService.changeNickname(player, nickname, new DefaultCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				listeners.onNicknameUpdate(nickname);
				updatePlayerCommandList();
			}
		});
	}

	public void updateAll() {
		updateScores();
		updatePlayerCommandList();
		updateUndoCommand();
		updateMessageList();
	}

	private void updatePlayerCommandList() {
		playerCommandService.getPlayerCommandStack(10, new DefaultCallback<List<PlayerCommandDto>>() {

			@Override
			public void onSuccess(List<PlayerCommandDto> result) {
				listeners.onPlayerCommandListUpdate(result);
			}
		});

	}

	private void updateMessageList() {
		playerCommandService.getSendMessageCommandStack(10, new DefaultCallback<List<PlayerCommandDto>>() {

			@Override
			public void onSuccess(List<PlayerCommandDto> result) {
				listeners.onMessageListUpdate(result);
			}
		});

	}

	private void updateScores() {
		scoreService.getPlayerList(new DefaultCallback<List<PlayerScoreDto>>() {

			@Override
			public void onSuccess(List<PlayerScoreDto> result) {
				listeners.onScoreChange(result);
			}

		});

	}

	private void updateUndoCommand() {
		playerCommandService.getUndoCommand(new DefaultCallback<PlayerCommandDto>() {

			@Override
			public void onSuccess(PlayerCommandDto result) {
				listeners.onUndoCommandUpdate(result);
			}
		});
	}

}
