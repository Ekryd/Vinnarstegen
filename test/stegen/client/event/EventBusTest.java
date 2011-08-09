package stegen.client.event;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.service.*;

public class EventBusTest {

	@Test
	public void testUserLoginStatus() {
		PlayerServiceAsync playerService = createStrictMock(PlayerServiceAsync.class);
		EventBus eventBus = new EventBusImpl(null, null, playerService);

		playerService.userLoginStatus("url", null);
		replay(playerService);

		eventBus.checkUserLoginStatus("url");
	}

}
