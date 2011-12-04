package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class UpdateIsNewUserPasswordOkCallback extends EventCallback<Boolean> {

	@Override
	public final Class<UpdateIsNewUserPasswordOkCallback> getCallbackClass() {
		return UpdateIsNewUserPasswordOkCallback.class;
	}
}
