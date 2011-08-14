package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class ChangedMessagesCallback extends EventCallback<List<PlayerCommandDto>> {

	@Override
	public final Class<ChangedMessagesCallback> getCallbackClass() {
		return ChangedMessagesCallback.class;
	}
}
