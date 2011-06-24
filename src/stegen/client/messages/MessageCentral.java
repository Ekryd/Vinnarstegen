package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;

public class MessageCentral {
	private final UpdateAllCallback updateAllCallback = new UpdateAllCallback();
	private final PlayerCommandServiceAsync playerCommandService = GWT.create(PlayerCommandService.class);
	private final ScoreServiceAsync scoreService = GWT.create(ScoreService.class);
	private final PlayerServiceAsync playerService = GWT.create(PlayerService.class);
	public final ListenerLists listeners = new ListenerLists();

	public void playerWonOverPlayer(PlayerDto winner, PlayerDto loser, GameResultDto gameResult, PlayerDto changedBy) {
		scoreService.playerWonOverPlayer(winner, loser, gameResult, changedBy, updateAllCallback);
	}

	public void clearAllScores(PlayerDto changedBy) {
		scoreService.clearAllScores(changedBy, updateAllCallback);
	}

	public void undo(final PlayerDto player) {
		playerCommandService.undoPlayerCommand(player, new DefaultCallback<UndoPlayerCommandResult>() {

			@Override
			public void onSuccess(UndoPlayerCommandResult result) {
				listeners.onUndoCommand(result);
				updateScoreAndCommands();
				updateNickname(player);
			}

		});
	}

	private void updateNickname(PlayerDto player) {
		playerService.getNickname(player.email, new DefaultCallback<String>() {

			@Override
			public void onSuccess(String result) {
				listeners.onNicknameUpdate(result);
			}
		});
	}

	public void sendMessage(PlayerDto player, String message) {
		playerService.sendMessage(player, message, new DefaultCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				updateUndoList();
				updateUndoCommand();
			}

		});
	}

	public void userLoginStatus(DefaultCallback<LoginDataDto> defaultCallback) {
		playerService.userLoginStatus(GWT.getHostPageBaseURL(), defaultCallback);
	}

	public void changeNickname(EmailAddressDto player, final String nickname) {
		playerService.changeNickname(player, nickname, new DefaultCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				updateScoreAndCommands();
				listeners.onNicknameUpdate(nickname);
			}
		});
	}

	public void updateScoreAndCommands() {
		updateScores();
		updateUndoList();
		updateUndoCommand();
	}

	private void updateUndoList() {
		playerCommandService.getPlayerCommandStack(10, new DefaultCallback<List<PlayerCommandDto>>() {

			@Override
			public void onSuccess(List<PlayerCommandDto> result) {
				listeners.onPlayerCommandListUpdate(result);
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

	private class UpdateAllCallback extends DefaultCallback<Void> {
		@Override
		public void onSuccess(Void result) {
			updateScoreAndCommands();
		}

	}

}
