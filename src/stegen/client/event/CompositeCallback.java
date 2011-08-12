package stegen.client.event;

import java.util.*;

import com.google.gwt.user.client.rpc.*;

class CompositeCallback<T> implements AsyncCallback<T> {

	private final ArrayList<AsyncCallback<?>> callbacks;

	public CompositeCallback(ArrayList<AsyncCallback<?>> callbacks) {
		this.callbacks = callbacks;
	}

	@Override
	public void onFailure(Throwable caught) {
		for (AsyncCallback<?> callback : callbacks) {
			callback.onFailure(caught);
		}
	}

	@Override
	public void onSuccess(T result) {
		for (AsyncCallback<?> callback : callbacks) {
			@SuppressWarnings("unchecked")
			AsyncCallback<T> callbackWithType = (AsyncCallback<T>) callback;
			callbackWithType.onSuccess(result);
		}
	}

}
