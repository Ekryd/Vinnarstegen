package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class PlayerWonCallback extends EventCallback<Void> {

	@Override
	public final Class<PlayerWonCallback> getCallbackClass() {
		return PlayerWonCallback.class;
	}

}
