package stegen.client.event;

import static org.mockito.Mockito.*;

import org.junit.*;

public class AbstractAsyncCallbackTest {

	@Test
	public void testOnFailurePrintStackTrace() {
		AbstractAsyncCallback<Void> emptyCallback = new AbstractAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
			}
		};
		
		Throwable caught = mock(Throwable.class);
		emptyCallback.onFailure(caught);
		verify(caught).printStackTrace();
	}

	@Test
	public void testOnSuccessDoNothing() {
		AbstractAsyncCallback<Runnable> emptyCallback = new AbstractAsyncCallback<Runnable>() {
			
			@Override
			public void onSuccess(Runnable result) {
				result.run();
			}
		};
		
		Runnable runnable = mock(Runnable.class);
		emptyCallback.onSuccess(runnable);
		verify(runnable).run();
	}
}
