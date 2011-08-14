package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class RegisterPlayerCallback extends EventCallback<Void> {

	@Override
	public final Class<RegisterPlayerCallback> getCallbackClass() {
		return RegisterPlayerCallback.class;
	}

}
