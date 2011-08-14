package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class CheckUserLoginStatusCallback extends EventCallback<LoginDataDto> {

	@Override
	public final Class<CheckUserLoginStatusCallback> getCallbackClass() {
		return CheckUserLoginStatusCallback.class;
	}

}
