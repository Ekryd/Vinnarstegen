package stegen.client.event;

import com.google.gwt.user.client.rpc.*;

public interface CallbackRepository {

	void clear();

	void add(EventType event, AsyncCallback<?> callback);

	AsyncCallback<?> get(EventType event);

	<T> AsyncCallback<T> get(EventType event, Class<T> asynchReturnClass);

}
