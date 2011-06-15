package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

public interface UndoServiceAsync {

	void undoPlayerCommand(EmailAddressDto player, AsyncCallback<UndoPlayerCommandResult> callback);

	void getUndoCommand(AsyncCallback<PlayerCommandDto> callback);

	void getPlayerCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

}
