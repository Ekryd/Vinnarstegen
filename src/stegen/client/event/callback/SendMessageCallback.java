package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class SendMessageCallback extends EventCallback<Void> {

	@Override
	public EventType getEventType() {
		return EventType.SEND_MESSAGE;
	}

}
