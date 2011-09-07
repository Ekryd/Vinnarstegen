package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandSendMessageCallback extends EventCallback<Void> {

	@Override
	public final Class<CommandSendMessageCallback> getCallbackClass() {
		return CommandSendMessageCallback.class;
	}

}
