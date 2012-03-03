package stegen.client;

import stegen.client.gui.desktop.*;

import com.google.gwt.core.client.*;
import com.google.gwt.user.client.ui.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 * 
 * @author Askia Linder, 178+2 ms
 */
public class Stegen implements EntryPoint {

	@Override
	public void onModuleLoad() {
		RootLayoutPanel.get().add(new ShellDesktop());
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		clientFactory.getApp().start(GWT.getHostPageBaseURL());
		
	}
}
