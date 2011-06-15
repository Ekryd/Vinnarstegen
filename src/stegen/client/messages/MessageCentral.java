package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;
import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.*;

public class MessageCentral {
	private final UpdateScoresCallback updateScoresCallback = new UpdateScoresCallback();
	private final UndoServiceAsync undoService = GWT.create(UndoService.class);
	private final ScoreServiceAsync scoreService = GWT.create(ScoreService.class);
	private final List<ScoreListener> scoreListeners = new ArrayList<ScoreListener>();
	private final List<UndoListener> undoListeners = new ArrayList<UndoListener>();

	public void addListener(ScoreListener listener) {
		scoreListeners.add(listener);
	}

	public void addListener(UndoListener listener) {
		undoListeners.add(listener);
	}

	public void playerWonOverPlayer(EmailAddressDto winEmail, EmailAddressDto loseEmail, GameResultDto gameResult,
			EmailAddressDto changedBy) {
		scoreService.playerWonOverPlayer(winEmail, loseEmail, gameResult, changedBy, updateScoresCallback);
	}

	public void clearAllScores(EmailAddressDto changedBy) {
		scoreService.clearAllScores(changedBy, updateScoresCallback);
	}

	public void undo(EmailAddressDto changedBy) {
		undoService.undoPlayerCommand(changedBy, new DefaultCallback<UndoPlayerCommandResult>() {

			@Override
			public void onSuccess(UndoPlayerCommandResult result) {
				for (UndoListener undoListener : undoListeners) {
					undoListener.onUndoCommand(result);
				}
				if (result == UndoPlayerCommandResult.SUCCESS) {
					updateUndoList();
					updateScores();
					updateUndoCommand();
				}
			}

		});
	}

	public void updateUndoList() {
		undoService.getPlayerCommandStack(10, new DefaultCallback<List<PlayerCommandDto>>() {

			@Override
			public void onSuccess(List<PlayerCommandDto> result) {
				for (UndoListener undoListener : undoListeners) {
					undoListener.onUndoListUpdate(result);
				}
			}
		});

	}

	public void updateScores() {
		scoreService.getPlayerList(new DefaultCallback<List<PlayerScoreDto>>() {

			@Override
			public void onSuccess(List<PlayerScoreDto> result) {
				for (ScoreListener scoreListener : scoreListeners) {
					scoreListener.onScoreChange(result);
				}
			}

		});

	}

	public void updateUndoCommand() {
		undoService.getUndoCommand(new DefaultCallback<PlayerCommandDto>() {

			@Override
			public void onSuccess(PlayerCommandDto result) {
				for (UndoListener undoListener : undoListeners) {
					undoListener.onUndoCommandUpdate(result);
				}
			}
		});
	}

	private static abstract class DefaultCallback<T> implements AsyncCallback<T> {

		@Override
		public final void onFailure(Throwable caught) {
			caught.printStackTrace();
		}
	}

	private class UpdateScoresCallback extends DefaultCallback<Void> {
		@Override
		public void onSuccess(Void result) {
			updateScores();
			updateUndoList();
			updateUndoCommand();
		}

	}

}
