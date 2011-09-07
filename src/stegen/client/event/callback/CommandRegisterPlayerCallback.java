package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandRegisterPlayerCallback extends EventCallback<Void> {

	@Override
	public final Class<CommandRegisterPlayerCallback> getCallbackClass() {
		return CommandRegisterPlayerCallback.class;
	}

}
