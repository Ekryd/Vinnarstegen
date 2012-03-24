package stegen.client.event;

import com.google.gwt.event.shared.*;

public class ChallengeEvent extends GwtEvent<ChallengeEventHandler> {
	
	public static final Type<ChallengeEventHandler> TYPE = new Type<ChallengeEventHandler>();

	public ChallengeEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ChallengeEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChallengeEventHandler handler) {
		handler.handleEvent(this);
	}
}
