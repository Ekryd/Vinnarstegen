package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandIsNUPCallback extends EventCallback<Boolean> {

	@Override
	public final Class<CommandIsNUPCallback> getCallbackClass() {
		return CommandIsNUPCallback.class;
	}

}
