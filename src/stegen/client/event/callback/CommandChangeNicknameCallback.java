package stegen.client.event.callback;

import stegen.client.event.*;

public abstract class CommandChangeNicknameCallback extends EventCallback<String> {

	@Override
	public final Class<CommandChangeNicknameCallback> getCallbackClass() {
		return CommandChangeNicknameCallback.class;
	}

}
