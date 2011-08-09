package stegen.client.service;

import java.util.*;

import stegen.client.dto.*;
import stegen.shared.*;

import com.google.gwt.user.client.rpc.*;

public interface PlayerCommandServiceAsync {

	void undoPlayerCommand(PlayerDto player, AsyncCallback<UndoPlayerCommandResult> callback);

	void getUndoCommand(AsyncCallback<PlayerCommandDto> callback);

	void getSendMessageCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

	void getGameResultCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

	void getLoginStatusCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

	void getPlayerMiscCommandStack(int maxDepth, AsyncCallback<List<PlayerCommandDto>> callback);

}
