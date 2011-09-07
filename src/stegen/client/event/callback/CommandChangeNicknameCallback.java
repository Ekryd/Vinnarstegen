package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class CommandChangeNicknameCallback extends EventCallback<PlayerDto> {

	@Override
	public final Class<CommandChangeNicknameCallback> getCallbackClass() {
		return CommandChangeNicknameCallback.class;
	}

}
