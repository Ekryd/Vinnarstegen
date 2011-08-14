package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class ClearScoresCallback extends EventCallback<Void> {

	@Override
	public final Class<ClearScoresCallback> getCallbackClass() {
		return ClearScoresCallback.class;
	}
}
