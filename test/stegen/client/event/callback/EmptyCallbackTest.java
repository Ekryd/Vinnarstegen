package stegen.client.event.callback;

import static org.easymock.EasyMock.*;

import org.junit.*;

public class EmptyCallbackTest {

	@Test
	public void testOnFailurePrintStackTrace() {
		EmptyCallback<Void> emptyCallback = new EmptyCallback<Void>();
		Throwable caught = createStrictMock(Throwable.class);
		caught.printStackTrace();
		replay(caught);
		emptyCallback.onFailure(caught);
		verify(caught);
	}

	@Test
	public void testOnSuccessDoNothing() {
		EmptyCallback<Runnable> emptyCallback = new EmptyCallback<Runnable>();
		Runnable runnable = createStrictMock(Runnable.class);
		replay(runnable);
		emptyCallback.onSuccess(runnable);
		verify(runnable);
	}

}
