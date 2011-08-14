package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class SendMessageCallback extends EventCallback<Void> {

	@Override
	public final Class<SendMessageCallback> getCallbackClass() {
		return SendMessageCallback.class;
	}

}
