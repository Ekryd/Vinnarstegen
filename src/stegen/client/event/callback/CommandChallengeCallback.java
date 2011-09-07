package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandChallengeCallback extends EventCallback<Void> {

	@Override
	public final Class<CommandChallengeCallback> getCallbackClass() {
		return CommandChallengeCallback.class;
	}
}
