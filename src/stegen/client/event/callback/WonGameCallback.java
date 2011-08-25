package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class WonGameCallback extends EventCallback<Void> {

	@Override
	public final Class<WonGameCallback> getCallbackClass() {
		return WonGameCallback.class;
	}
}
