package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class ChangedScoresCallback extends EventCallback<List<PlayerScoreDto>> {

	@Override
	public final Class<ChangedScoresCallback> getCallbackClass() {
		return ChangedScoresCallback.class;
	}
}
