package stegen.client.event;

public interface CallbackRepository {

	void clear();

	<R> void add(EventCallback<R> callback);

	<T extends EventCallback<?>> T get(Class<T> callbackClass);
}
