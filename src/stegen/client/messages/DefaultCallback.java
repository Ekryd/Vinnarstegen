package stegen.client.messages;

import com.google.gwt.user.client.rpc.*;

public abstract class DefaultCallback<T> implements AsyncCallback<T> {

	@Override
	public final void onFailure(Throwable caught) {
		caught.printStackTrace();
	}
}