package stegen.client.event;

import com.google.gwt.event.shared.*;

public class GamePlayedEvent extends GwtEvent<GamePlayedEventHandler> {
	
	public static final Type<GamePlayedEventHandler> TYPE = new Type<GamePlayedEventHandler>();

	public GamePlayedEvent() {
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<GamePlayedEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GamePlayedEventHandler handler) {
		handler.handleEvent(this);
	}
}
