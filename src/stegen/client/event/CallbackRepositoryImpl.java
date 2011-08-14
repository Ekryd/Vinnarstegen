package stegen.client.event;

import java.util.*;

public class CallbackRepositoryImpl implements CallbackRepository {

	private final Map<Class<?>, EventCallback<?>> store = new HashMap<Class<?>, EventCallback<?>>();

	@Override
	public void clear() {
		store.clear();
	}

	@Override
	public <R> void add(EventCallback<R> callback) {
		Class<? extends EventCallback<R>> key = callback.getCallbackClass();
		if (store.containsKey(key)) {
			@SuppressWarnings("unchecked")
			EventCallback<R> eventCallback = (EventCallback<R>) store.get(key);
			eventCallback.merge(callback);
		} else {
			store.put(key, callback);
		}
	}

	@Override
	public <T extends EventCallback<?>> T get(Class<T> callbackClass) {
		@SuppressWarnings("unchecked")
		T callback = (T) store.get(callbackClass);
		return callback;
	}

}
