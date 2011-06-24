package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

//TODO
public interface PlayerCommandListener {

	void onUndoCommand(UndoPlayerCommandResult result);

	void onPlayerCommandListUpdate(List<PlayerCommandDto> result);

	void onUndoCommandUpdate(PlayerCommandDto result);
}
