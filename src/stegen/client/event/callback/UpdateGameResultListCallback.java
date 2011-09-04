package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdateGameResultListCallback extends EventCallback<List<PlayerCommandDto>> {

	@Override
	public final Class<UpdateGameResultListCallback> getCallbackClass() {
		return UpdateGameResultListCallback.class;
	}

}
