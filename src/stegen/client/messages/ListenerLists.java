package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

public class ListenerLists {
	private final List<ScoreListener> scoreListenerList = new ArrayList<ScoreListener>();
	private final List<PlayerCommandListener> undoListeners = new ArrayList<PlayerCommandListener>();
	private final List<NicknameListener> nicknameListeners = new ArrayList<NicknameListener>();
	private final List<MessageListener> messageListeners = new ArrayList<MessageListener>();

	public void addListener(ScoreListener listener) {
		scoreListenerList.add(listener);
	}

	public void addListener(PlayerCommandListener listener) {
		undoListeners.add(listener);
	}

	public void addListener(NicknameListener listener) {
		nicknameListeners.add(listener);
	}

	public void addListener(MessageListener listener) {
		messageListeners.add(listener);
	}

	void onUndoCommand(UndoPlayerCommandResult result) {
		for (PlayerCommandListener undoListener : undoListeners) {
			undoListener.onUndoCommand(result);
		}
	}

	void onPlayerMiscCommandListUpdate(List<PlayerCommandDto> result) {
		for (PlayerCommandListener listener : undoListeners) {
			listener.onPlayerMiscCommandListUpdate(result);
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

	void onNicknameUpdate(String nickname) {
		for (NicknameListener listener : nicknameListeners) {
			listener.onNicknameChange(nickname);
		}
	}

	void onMessageListUpdate(List<PlayerCommandDto> result) {
		for (MessageListener listener : messageListeners) {
			listener.onMessageListUpdate(result);
		}
	}

	public void onGameResultListUpdate(List<PlayerCommandDto> result) {
		for (PlayerCommandListener listener : undoListeners) {
			listener.onGameResultListUpdate(result);
		}
	}

	public void onLoginStatusListUpdate(List<PlayerCommandDto> result) {
		for (PlayerCommandListener listener : undoListeners) {
			listener.onLoginStatusListUpdate(result);
		}
	}

}
