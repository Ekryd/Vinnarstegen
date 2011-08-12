package stegen.client.event;

import java.util.*;

import com.google.gwt.user.client.rpc.*;

public class CallbackRepositoryImpl implements CallbackRepository {

	private final Map<EventType, ArrayList<AsyncCallback<?>>> store = new HashMap<EventType, ArrayList<AsyncCallback<?>>>();

	public void clear() {
		store.clear();
	}

	public void add(EventType event, AsyncCallback<?> callback) {
		getList(event).add(callback);
	}

	public AsyncCallback<?> get(EventType event) {
		return get(event, Object.class);
	}

	public <T> AsyncCallback<T> get(EventType event, Class<T> asynchReturnClass) {
		ArrayList<AsyncCallback<?>> callbacks = getList(event);
		return new CompositeCallback<T>(callbacks);
	}

	private ArrayList<AsyncCallback<?>> getList(EventType event) {
		if (!store.containsKey(event)) {
			addNewListToStore(event);
		}
		return store.get(event);
	}

	private void addNewListToStore(EventType event) {
		store.put(event, new ArrayList<AsyncCallback<?>>());
	}

}
