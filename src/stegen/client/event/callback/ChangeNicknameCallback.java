package stegen.client.event.callback;

import stegen.client.event.*;
import stegen.shared.*;

public abstract class ChangeNicknameCallback extends EventCallback<PlayerDto> {

	@Override
	public final Class<ChangeNicknameCallback> getCallbackClass() {
		return ChangeNicknameCallback.class;
	}

}
