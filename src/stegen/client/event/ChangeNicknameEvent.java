package stegen.client.event;

import com.google.gwt.event.shared.*;

public class ChangeNicknameEvent extends GwtEvent<ChangeNicknameEventHandler> {
	final private String newNickname;
	
	public static final Type<ChangeNicknameEventHandler> TYPE = new Type<ChangeNicknameEventHandler>();

	public ChangeNicknameEvent(String newNickname) {
		this.newNickname = newNickname;
		
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChangeNicknameEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeNicknameEventHandler handler) {
		handler.handleEvent(this);
	}

	public String getNewNickname() {
		return newNickname;
	}

	
}
