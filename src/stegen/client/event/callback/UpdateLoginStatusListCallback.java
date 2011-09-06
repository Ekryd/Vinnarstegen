package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdateLoginStatusListCallback extends EventCallback<List<PlayerCommandDto>> {

	@Override
	public final Class<UpdateLoginStatusListCallback> getCallbackClass() {
		return UpdateLoginStatusListCallback.class;
	}

}
