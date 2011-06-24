package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;

import com.google.gwt.user.client.rpc.*;

public interface PlayerCommandServiceAsync {

	void undoPlayerCommand(PlayerDto player, AsyncCallback<UndoPlayerCommandResult> callback);

	void getUndoCommand(AsyncCallback<PlayerCommandDto> callback);

	void getPlayerCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

	void getSendMessageCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

}
