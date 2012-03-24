package stegen.client.event;

import stegen.shared.*;

import com.google.gwt.event.shared.*;

public class LoginEvent extends GwtEvent<LoginEventHandler> {
	final private LoginDataDto loginData;
	
	public static final Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

	public LoginEvent(LoginDataDto loginData) {
		this.loginData = loginData;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.handleEvent(this);
	}

	public LoginDataDto getLoginData() {
		return loginData;
	}
}
