package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

@RemoteServiceRelativePath("undo")
public interface UndoService extends RemoteService {
	List<PlayerCommandDto> getPlayerCommandStack(int maxDepth);

	UndoPlayerCommandResult undoPlayerCommand(EmailAddressDto player);

	PlayerCommandDto getUndoCommand();
}
