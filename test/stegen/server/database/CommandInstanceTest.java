package stegen.server.database;

import org.junit.*;

import stegen.client.dto.*;
import stegen.server.command.*;

import com.google.appengine.tools.development.testing.*;

public class CommandInstanceTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void serializeUserLoggedIn() {
		CommandInstance command = CommandInstanceFactory.getUserIsLoggedInAndRegistered();
		Assert.assertEquals("{\"result\":"
				+ "{\"logoutUrl\":\"logoutUrl\",\"loginResponse\":\"LOGGED_IN_AND_REGISTERED\",\"signInUrl\":\"\","
				+ "\"player\":{\"email\":{\"address\":\"address\"},\"nickname\":\"nickname\"}}}",
				command.getCommandSerialized());
	}

	@Test
	public void serializeCleanAllScores() {
		PlayerRepository playerRepository = PlayerRepository.get();
		EmailAddressDto playerEmail = new EmailAddressDto("address");
		Player player = Player.createPlayer(playerEmail);
		playerRepository.create(player);
		CommandInstance command = CommandInstanceFactory.clearAllScores(playerEmail);
		Assert.assertEquals(
				"{\"oldScores\":[{\"playerEmail\":{\"address\":\"address\"},\"score\":0}],\"changedBy\":{\"address\":\"address\"}}",
				command.getCommandSerialized());
	}

	@Test
	public void deserializeCleanAllScores() {
		PlayerRepository playerRepository = PlayerRepository.get();
		EmailAddressDto playerEmail = new EmailAddressDto("address");
		Player player = Player.createPlayer(playerEmail);
		playerRepository.create(player);
		CommandInstance command = CommandInstanceFactory.clearAllScores(playerEmail);
		String ser = command.getCommandSerialized();
		System.out.println(ser);
		PlayerCommand deserialize = new Serializer().deserialize(ser, ClearAllScores.class);
		Assert.assertNotNull(deserialize.getDescription());
	}

	@Test
	public void deserializeUserLoggedIn() {
		EmailAddressDto playerEmail1 = new EmailAddressDto("winner");
		EmailAddressDto playerEmail2 = new EmailAddressDto("loser");
		CommandInstanceFactory.registerPlayer(playerEmail1).getCommand().execute();
		CommandInstanceFactory.registerPlayer(playerEmail2).getCommand().execute();
		CommandInstanceFactory.playerWonOverPlayer(playerEmail1, playerEmail2, playerEmail1).getCommand().execute();
		CommandInstance command = CommandInstanceFactory.clearAllScores(playerEmail1);
		String serialized = command.getCommandSerialized();
		System.out.println(serialized);
		ClearAllScores deserialize = new Serializer().deserialize(serialized, ClearAllScores.class);
		serialized = new Serializer().deepSerialize(deserialize);
		Assert.assertEquals(
				"{\"oldScores\":[{\"playerEmail\":{\"address\":\"winner\"},\"score\":1},{\"playerEmail\":{\"address\":\"loser\"},\"score\":0}],"
						+ "\"changedBy\":{\"address\":\"winner\"}}", serialized);
	}
}
