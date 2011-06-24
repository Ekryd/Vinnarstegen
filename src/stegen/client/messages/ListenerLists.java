package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

public class ListenerLists {
	private final List<ScoreListener> scoreListenerList = new ArrayList<ScoreListener>();
	private final List<PlayerCommandListener> undoListeners = new ArrayList<PlayerCommandListener>();
	private final List<NicknameListener> nicknameListeners = new ArrayList<NicknameListener>();

	public void addListener(ScoreListener listener) {
		scoreListenerList.add(listener);
	}

	public void addListener(PlayerCommandListener listener) {
		undoListeners.add(listener);
	}

	public void addListener(NicknameListener listener) {
		nicknameListeners.add(listener);
	}

	void onUndoCommand(UndoPlayerCommandResult result) {
		for (PlayerCommandListener undoListener : undoListeners) {
			undoListener.onUndoCommand(result);
		}
	}

	void onPlayerCommandListUpdate(List<PlayerCommandDto> result) {
		for (PlayerCommandListener undoListener : undoListeners) {
			undoListener.onPlayerCommandListUpdate(result);
		}
	}

	void onScoreChange(List<PlayerScoreDto> result) {
		for (ScoreListener listener : scoreListenerList) {
			listener.onScoreChange(result);
		}
	}

	void onUndoCommandUpdate(PlayerCommandDto result) {
		for (PlayerCommandListener undoListener : undoListeners) {
			undoListener.onUndoCommandUpdate(result);
		}
	}

	public void onNicknameUpdate(String nickname) {
		for (NicknameListener listener : nicknameListeners) {
			listener.onNicknameChange(nickname);
		}
	}

}
