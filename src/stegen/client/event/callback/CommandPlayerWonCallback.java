package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandPlayerWonCallback extends EventCallback<Void> {

	@Override
	public final Class<CommandPlayerWonCallback> getCallbackClass() {
		return CommandPlayerWonCallback.class;
	}

}
