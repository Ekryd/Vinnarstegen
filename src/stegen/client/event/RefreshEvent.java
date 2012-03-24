package stegen.client.event;

import stegen.shared.*;

import com.google.gwt.event.shared.*;

public class RefreshEvent extends GwtEvent<RefreshEventHandler> {
	final private RefreshType refreshType;
	
	public static final Type<RefreshEventHandler> TYPE = new Type<RefreshEventHandler>();

	public RefreshEvent(RefreshType result) {
		this.refreshType = result;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<RefreshEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(RefreshEventHandler handler) {
		handler.handleEvent(this);
	}

	public RefreshType getRefreshType() {
		return refreshType;
	}

	
}
