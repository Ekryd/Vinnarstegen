package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdateUndoCommandCallback extends EventCallback<PlayerCommandDto> {

	@Override
	public final Class<UpdateUndoCommandCallback> getCallbackClass() {
		return UpdateUndoCommandCallback.class;
	}

}
