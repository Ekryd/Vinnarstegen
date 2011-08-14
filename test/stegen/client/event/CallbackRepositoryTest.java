package stegen.client.event;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import stegen.client.event.callback.*;

public class CallbackRepositoryTest {

	@Test
	public void testAddAndGet() {
		CallbackRepository store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback callback = createCallback();
		store.add(callback);

		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);
		assertTrue(actual != null);
	}

	@Test
	public void testExecutesOnSuccess() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback callback = createCallback();
		store.add(callback);

		callback.onSuccessImpl(null);
		replay(callback);

		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);
		actual.onSuccess(null);

		verify(callback);
	}

	@Test
	public void testExecutesOnFailure() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback callback = createCallback();
		store.add(callback);

		RuntimeException caught = new RuntimeException();
		callback.onFailure(caught);
		replay(callback);

		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);
		actual.onFailure(caught);

		verify(callback);
	}

	@Test
	public void testExecutesManyOnSuccess() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback callback1 = createCallback();
		UpdatePlayerScoreListCallback callback2 = createCallback();
		UpdatePlayerScoreListCallback callback3 = createCallback();
		store.add(callback1);
		store.add(callback2);
		store.add(callback3);

		callback1.onSuccessImpl(null);
		callback2.onSuccessImpl(null);
		callback3.onSuccessImpl(null);
		replay(callback1, callback2, callback3);

		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);
		actual.onSuccess(null);

		verify(callback1, callback2, callback3);
	}

	@Test
	public void testExecutesOnlyOriginalOnFailure() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback callback1 = createCallback();
		UpdatePlayerScoreListCallback callback2 = createCallback();
		UpdatePlayerScoreListCallback callback3 = createCallback();
		store.add(callback1);
		store.add(callback2);
		store.add(callback3);

		RuntimeException caught = new RuntimeException();
		callback1.onFailure(caught);
		replay(callback1, callback2, callback3);

		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);
		actual.onFailure(caught);

		verify(callback1, callback2, callback3);
	}

	@Test
	public void testNoCallbacks() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);

		assertNull(actual);
	}

	@Test
	public void testClear() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		UpdatePlayerScoreListCallback callback1 = createCallback();
		UpdatePlayerScoreListCallback callback2 = createCallback();
		UpdatePlayerScoreListCallback callback3 = createCallback();
		store.add(callback1);
		store.add(callback2);
		store.add(callback3);

		replay(callback1, callback2, callback3);

		store.clear();
		UpdatePlayerScoreListCallback actual = store.get(UpdatePlayerScoreListCallback.class);
		assertNull(actual);

		verify(callback1, callback2, callback3);
	}

	private UpdatePlayerScoreListCallback createCallback() {
		return createMockBuilder(UpdatePlayerScoreListCallback.class).withConstructor().createMock();
	}

}
