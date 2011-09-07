package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdateLoginStatusCallback extends EventCallback<LoginDataDto> {

	@Override
	public final Class<UpdateLoginStatusCallback> getCallbackClass() {
		return UpdateLoginStatusCallback.class;
	}

}
