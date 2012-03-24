package stegen.client.event;

import com.google.gwt.user.client.rpc.*;

public abstract class AbstractAsyncCallback<T> implements AsyncCallback<T> {

	@Override
	public final void onFailure(Throwable caught) {
		caught.printStackTrace();
	}
}
