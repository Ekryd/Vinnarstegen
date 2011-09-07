package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandRefreshCallback extends EventCallback<Void> {

	@Override
	public final Class<CommandRefreshCallback> getCallbackClass() {
		return CommandRefreshCallback.class;
	}
}
