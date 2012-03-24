package stegen.client.event;

import stegen.shared.*;

import com.google.gwt.event.shared.*;

public class UndoEvent extends GwtEvent<UndoEventHandler> {
	final private UndoPlayerCommandResult result;
	
	public static final Type<UndoEventHandler> TYPE = new Type<UndoEventHandler>();

	public UndoEvent(UndoPlayerCommandResult result) {
		this.result = result;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UndoEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UndoEventHandler handler) {
		handler.handleEvent(this);
	}

	public UndoPlayerCommandResult getResult() {
		return result;
	}

	
}
