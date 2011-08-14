package stegen.client.event;

import java.util.*;

import com.google.gwt.user.client.rpc.*;

public abstract class EventCallback<T> implements AsyncCallback<T> {

	private List<EventCallback<T>> mergedCallbacks = new ArrayList<EventCallback<T>>();

	public abstract Class<? extends EventCallback<T>> getCallbackClass();

	public abstract void onSuccessImpl(T result);

	@Override
	public final void onFailure(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public final void onSuccess(T result) {
		performOriginalCallback(result);
		performMergedCallbacks(result);
	}

	private void performOriginalCallback(T result) {
		onSuccessImpl(result);
	}

	private void performMergedCallbacks(T result) {
		for (EventCallback<T> mergedCallback : mergedCallbacks) {
			mergedCallback.onSuccess(result);
		}
	}

	void merge(EventCallback<T> other) {
		mergedCallbacks.add(other);
	}
}
