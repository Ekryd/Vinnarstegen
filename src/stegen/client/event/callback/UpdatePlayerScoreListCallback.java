package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdatePlayerScoreListCallback extends EventCallback<List<PlayerScoreDto>> {

	@Override
	public final Class<UpdatePlayerScoreListCallback> getCallbackClass() {
		return UpdatePlayerScoreListCallback.class;
	}
}
