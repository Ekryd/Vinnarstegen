package stegen.client.event.callback;

import com.google.gwt.user.client.rpc.*;

public class EmptyCallback<T> implements AsyncCallback<T> {

	@Override
	public final void onFailure(Throwable caught) {
		caught.printStackTrace();
	}

	@Override
	public void onSuccess(T result) {}

}
