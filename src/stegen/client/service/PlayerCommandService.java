package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("undo")
public interface PlayerCommandService extends RemoteService {
	List<PlayerCommandDto> getPlayerCommandStack(int maxDepth);

	UndoPlayerCommandResult undoPlayerCommand(PlayerDto player);

	PlayerCommandDto getUndoCommand();

	List<PlayerCommandDto> getSendMessageCommandStack(int maxDepth);
}
