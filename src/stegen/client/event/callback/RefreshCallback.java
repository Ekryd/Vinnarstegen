package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class RefreshCallback extends EventCallback<Void> {

	@Override
	public final Class<RefreshCallback> getCallbackClass() {
		return RefreshCallback.class;
	}
}
