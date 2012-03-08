package stegen.client;

import com.google.gwt.core.client.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Askia Linder, 178+2 ms
 */
public class Stegen implements EntryPoint {

	@Override
	public void onModuleLoad() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		clientFactory.getApp().start(GWT.getHostPageBaseURL());
		
	}
}
