package stegen.client.event;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.google.gwt.user.client.rpc.*;

public class EventHandlerStoreTest {

	@Test
	public void testAddAndGet() {
		CallbackRepository store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback = createCallback();
		store.add(EventType.SEND_MESSAGE, callback);
		@SuppressWarnings("unchecked")
		AsyncCallback<Void> actual = (AsyncCallback<Void>) store.get(EventType.SEND_MESSAGE);
		assertTrue(actual != null);
	}

	@Test
	public void testGetWithType() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback = createCallback();
		store.add(EventType.SEND_MESSAGE, callback);
		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);
		assertTrue(actual != null);
	}

	@Test
	public void testExecutesOnSuccess() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback = createCallback();
		store.add(EventType.SEND_MESSAGE, callback);

		callback.onSuccess(null);
		replay(callback);

		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);
		actual.onSuccess(null);

		verify(callback);
	}

	@Test
	public void testExecutesOnFailure() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback = createCallback();
		store.add(EventType.SEND_MESSAGE, callback);

		callback.onFailure(null);
		replay(callback);

		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);
		actual.onFailure(null);

		verify(callback);
	}

	@Test
	public void testExecutesManyOnSuccess() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback1 = createCallback();
		AsyncCallback<Void> callback2 = createCallback();
		AsyncCallback<Void> callback3 = createCallback();
		store.add(EventType.SEND_MESSAGE, callback1);
		store.add(EventType.SEND_MESSAGE, callback2);
		store.add(EventType.SEND_MESSAGE, callback3);

		callback1.onSuccess(null);
		callback2.onSuccess(null);
		callback3.onSuccess(null);
		replay(callback1, callback2, callback3);

		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);
		actual.onSuccess(null);

		verify(callback1, callback2, callback3);
	}

	@Test
	public void testExecutesManyOnFailure() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback1 = createCallback();
		AsyncCallback<Void> callback2 = createCallback();
		AsyncCallback<Void> callback3 = createCallback();
		store.add(EventType.SEND_MESSAGE, callback1);
		store.add(EventType.SEND_MESSAGE, callback2);
		store.add(EventType.SEND_MESSAGE, callback3);

		callback1.onFailure(null);
		callback2.onFailure(null);
		callback3.onFailure(null);
		replay(callback1, callback2, callback3);

		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);
		actual.onFailure(null);

		verify(callback1, callback2, callback3);
	}

	@Test
	public void testNoCallbacks() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);

		assertNotNull(actual);

		actual.onFailure(null);
		actual.onSuccess(null);
	}

	@Test
	public void testClear() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		AsyncCallback<Void> callback1 = createCallback();
		AsyncCallback<Void> callback2 = createCallback();
		AsyncCallback<Void> callback3 = createCallback();
		store.add(EventType.SEND_MESSAGE, callback1);
		store.add(EventType.SEND_MESSAGE, callback2);
		store.add(EventType.SEND_MESSAGE, callback3);

		replay(callback1, callback2, callback3);

		store.clear();
		AsyncCallback<Void> actual = store.get(EventType.SEND_MESSAGE, Void.class);
		actual.onSuccess(null);

		verify(callback1, callback2, callback3);
	}

	@SuppressWarnings("unchecked")
	private AsyncCallback<Void> createCallback() {
		return createStrictMock(AsyncCallback.class);
	}

}
