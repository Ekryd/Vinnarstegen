package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

//TODO
public interface PlayerCommandListener {

	void onUndoCommand(UndoPlayerCommandResult result);

	void onPlayerMiscCommandListUpdate(List<PlayerCommandDto> result);

	void onUndoCommandUpdate(PlayerCommandDto result);

	void onGameResultListUpdate(List<PlayerCommandDto> result);

	void onLoginStatusListUpdate(List<PlayerCommandDto> result);
}
