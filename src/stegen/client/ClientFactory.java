package stegen.client;

import stegen.client.gui.*;

import com.google.gwt.event.shared.*;


public interface ClientFactory {

	AppController getApp();
	
	EventBus getEventBus();
	
	Shell getShell();

}
