package stegen.client;

import stegen.client.gui.*;


public interface ClientFactory {

	AppController getApp();
	
	Shell getShell();

}
