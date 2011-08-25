package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class ChallengeCallback extends EventCallback<Void> {

	@Override
	public final Class<ChallengeCallback> getCallbackClass() {
		return ChallengeCallback.class;
	}
}
