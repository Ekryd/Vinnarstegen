package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class CommandUndoCallback extends EventCallback<UndoPlayerCommandResult> {

	@Override
	public final Class<CommandUndoCallback> getCallbackClass() {
		return CommandUndoCallback.class;
	}
}
