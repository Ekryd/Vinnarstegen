package stegen.client.messages;

import java.util.*;

import stegen.client.dto.*;

public interface UndoListener {

	void onUndoCommand(UndoPlayerCommandResult result);

	void onUndoListUpdate(List<PlayerCommandDto> result);

	void onUndoCommandUpdate(PlayerCommandDto result);
}
