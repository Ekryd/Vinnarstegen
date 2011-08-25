package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UndoCallback extends EventCallback<UndoPlayerCommandResult> {

	@Override
	public final Class<UndoCallback> getCallbackClass() {
		return UndoCallback.class;
	}
}
