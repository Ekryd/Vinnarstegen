package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdateRefreshCallback extends EventCallback<RefreshType> {

	@Override
	public final Class<UpdateRefreshCallback> getCallbackClass() {
		return UpdateRefreshCallback.class;
	}
}
