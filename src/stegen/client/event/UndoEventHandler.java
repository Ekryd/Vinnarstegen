package stegen.client.event;

import com.google.gwt.event.shared.*;
public interface UndoEventHandler extends EventHandler {

	void handleEvent(UndoEvent event);
}
