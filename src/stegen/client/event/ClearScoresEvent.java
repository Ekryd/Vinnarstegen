package stegen.client.event;

import com.google.gwt.event.shared.*;

public class ClearScoresEvent extends GwtEvent<ClearScoresEventHandler> {
	
	public static final Type<ClearScoresEventHandler> TYPE = new Type<ClearScoresEventHandler>();

	public ClearScoresEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ClearScoresEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ClearScoresEventHandler handler) {
		handler.handleEvent(this);
	}
}
