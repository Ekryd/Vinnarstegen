package stegen.client.event;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.junit.*;

import stegen.client.event.callback.*;

public class CallbackRepositoryTest {

	@Test
	public void testAddAndGet() {
		CallbackRepository store = new CallbackRepositoryImpl();
		ChangedScoresCallback callback = createCallback();
		store.add(callback);

		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);
		assertTrue(actual != null);
	}

	@Test
	public void testExecutesOnSuccess() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		ChangedScoresCallback callback = createCallback();
		store.add(callback);

		callback.onSuccessImpl(null);
		replay(callback);

		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);
		actual.onSuccess(null);

		verify(callback);
	}

	@Test
	public void testExecutesOnFailure() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		ChangedScoresCallback callback = createCallback();
		store.add(callback);

		RuntimeException caught = new RuntimeException();
		callback.onFailure(caught);
		replay(callback);

		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);
		actual.onFailure(caught);

		verify(callback);
	}

	@Test
	public void testExecutesManyOnSuccess() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		ChangedScoresCallback callback1 = createCallback();
		ChangedScoresCallback callback2 = createCallback();
		ChangedScoresCallback callback3 = createCallback();
		store.add(callback1);
		store.add(callback2);
		store.add(callback3);

		callback1.onSuccessImpl(null);
		callback2.onSuccessImpl(null);
		callback3.onSuccessImpl(null);
		replay(callback1, callback2, callback3);

		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);
		actual.onSuccess(null);

		verify(callback1, callback2, callback3);
	}

	@Test
	public void testExecutesOnlyOriginalOnFailure() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		ChangedScoresCallback callback1 = createCallback();
		ChangedScoresCallback callback2 = createCallback();
		ChangedScoresCallback callback3 = createCallback();
		store.add(callback1);
		store.add(callback2);
		store.add(callback3);

		RuntimeException caught = new RuntimeException();
		callback1.onFailure(caught);
		replay(callback1, callback2, callback3);

		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);
		actual.onFailure(caught);

		verify(callback1, callback2, callback3);
	}

	@Test
	public void testNoCallbacks() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);

		assertNull(actual);
	}

	@Test
	public void testClear() {
		CallbackRepositoryImpl store = new CallbackRepositoryImpl();
		ChangedScoresCallback callback1 = createCallback();
		ChangedScoresCallback callback2 = createCallback();
		ChangedScoresCallback callback3 = createCallback();
		store.add(callback1);
		store.add(callback2);
		store.add(callback3);

		replay(callback1, callback2, callback3);

		store.clear();
		ChangedScoresCallback actual = store.get(ChangedScoresCallback.class);
		assertNull(actual);

		verify(callback1, callback2, callback3);
	}

	private ChangedScoresCallback createCallback() {
		return createMockBuilder(ChangedScoresCallback.class).withConstructor().createMock();
	}

}
