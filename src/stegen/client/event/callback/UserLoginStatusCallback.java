package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UserLoginStatusCallback extends EventCallback<LoginDataDto> {

	@Override
	public final Class<UserLoginStatusCallback> getCallbackClass() {
		return UserLoginStatusCallback.class;
	}

}
