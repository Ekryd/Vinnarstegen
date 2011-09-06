package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdatePlayerMiscCommandListCallback extends EventCallback<List<PlayerCommandDto>> {

	@Override
	public final Class<UpdatePlayerMiscCommandListCallback> getCallbackClass() {
		return UpdatePlayerMiscCommandListCallback.class;
	}

}
