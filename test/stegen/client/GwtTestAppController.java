package stegen.client;

import static org.easymock.EasyMock.*;

import org.junit.*;

import stegen.client.service.*;
import stegen.shared.*;

import com.google.gwt.junit.client.*;

@Ignore("This test does not work yet!")
public class GwtTestAppController extends GWTTestCase {

	@Test
	public void test() {
		LoginDataDto loginData = LoginDataDtoFactory.createLoginData();
		loginData = LoginDataDto.userIsLoggedInAndRegistered(loginData.player, "logoutUrl");
		PlayerCommandServiceAsync playerCommandService = createStrictMock(PlayerCommandServiceAsync.class);
		ScoreServiceAsync scoreService = createStrictMock(ScoreServiceAsync.class);
		PlayerServiceAsync playerService = createStrictMock(PlayerServiceAsync.class);
		AppController controller = new AppController(playerCommandService, scoreService, playerService,null,null);
		controller.eventCheckLoginStatusHandler.onSuccessImpl(loginData);
	}

	@Override
	public String getModuleName() {
		return "stegen.Stegen";
	}

}
