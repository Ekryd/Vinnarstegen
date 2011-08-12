package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class CheckUserLoginStatusCallback extends EventCallback<LoginDataDto> {

	@Override
	public EventType getEventType() {
		return EventType.CHECK_USER_LOGIN_STATUS;
	}

}
