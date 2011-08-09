package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("undo")
public interface PlayerCommandService extends RemoteService {
	List<PlayerCommandDto> getPlayerMiscCommandStack(int maxDepth);

	UndoPlayerCommandResult undoPlayerCommand(PlayerDto player);

	PlayerCommandDto getUndoCommand();

	List<PlayerCommandDto> getSendMessageCommandStack(int maxDepth);

	List<PlayerCommandDto> getLoginStatusCommandStack(int maxDepth);

	List<PlayerCommandDto> getGameResultCommandStack(int maxDepth);
}
