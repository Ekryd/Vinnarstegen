package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandClearScoresCallback extends EventCallback<Void> {

	@Override
	public final Class<CommandClearScoresCallback> getCallbackClass() {
		return CommandClearScoresCallback.class;
	}
}
