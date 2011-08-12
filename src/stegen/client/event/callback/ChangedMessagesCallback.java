package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class ChangedMessagesCallback extends EventCallback<List<PlayerCommandDto>> {

	@Override
	public EventType getEventType() {
		return EventType.CHANGED_MESSAGES;
	}

}
