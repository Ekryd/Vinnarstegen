package stegen.client;


import org.junit.*;

import stegen.client.service.*;

import com.google.gwt.core.client.*;
import com.google.gwt.junit.client.*;

//@Ignore("This test works, but is crazy slow. It doesnt feel like a JUnit test, more lika a Java-program")
public class AppControllerGWTTest extends GWTTestCase {

	private AppController test;
	
	
	
	@Override
	public String getModuleName() {
		return "stegen.Stegen";
	}
	
	@Test
	public void testShouldInitializeEventBus() {
		PlayerCommandServiceAsync playerCommandService = GWT.create(PlayerCommandService.class);
		ScoreServiceAsync scoreService = GWT.create(ScoreService.class);
		PlayerServiceAsync playerService = GWT.create(PlayerService.class);
		test = new AppController(playerCommandService, scoreService, playerService);
		assertNotNull(test.eventBus);
		
		test.start(GWT.getHostPageBaseURL());
	}

	

}
