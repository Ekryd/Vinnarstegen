package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class UpdateApplicationVersionCallback extends EventCallback<String> {

	@Override
	public final Class<UpdateApplicationVersionCallback> getCallbackClass() {
		return UpdateApplicationVersionCallback.class;
	}

}
