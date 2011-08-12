package stegen.client.event.callback;

import stegen.client.event.*;

import com.google.gwt.user.client.rpc.*;

public abstract class EventCallback<T> implements AsyncCallback<T> {

	public abstract EventType getEventType();

	@Override
	public void onFailure(Throwable caught) {
		caught.printStackTrace();
	}

}
