package stegen.client.event.callback;

import java.util.*;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class UpdateSendMessageListCallback extends EventCallback<List<PlayerCommandDto>> {

	@Override
	public final Class<UpdateSendMessageListCallback> getCallbackClass() {
		return UpdateSendMessageListCallback.class;
	}
}
