package stegen.server.database;

import org.junit.*;

import stegen.client.dto.*;
import stegen.server.command.*;

import com.google.appengine.tools.development.testing.*;

public class SerializerTest {
	private static final EmailAddressDto email = new EmailAddressDto("address");
	private static final EmailAddressDto email2 = new EmailAddressDto("address2");
	private static final PlayerDto player = new PlayerDto(email, "nickname");
	private static final PlayerDto player2 = new PlayerDto(email2, "nickname2");

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());

	@Before
	public void setUp() {
		helper.setUp();
		CommandInstanceFactory.registerPlayer(player.email).getCommand().execute();
		CommandInstanceFactory.registerPlayer(player2.email).getCommand().execute();
		CommandInstanceFactory.playerWonOverPlayer(player.email, player2.email, player.email).getCommand().execute();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testChangeNickname() {
		PlayerCommand command = new ChangeNickname(player, "nicknameNew");
		String actual = new Serializer().deepSerialize(command);
		Assert.assertEquals(
				"{\"email\":{\"address\":\"address\"},\"newNickname\":\"nicknameNew\",\"oldNickname\":\"nickname\"}",
				actual);
	}

	@Test
	public void testCheckLoginStatus() {
		LoginDataDto result = LoginDataDto.userIsLoggedInAndRegistered(player, "logoutUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", result);
		String actual = new Serializer().deepSerialize(command);
		Assert.assertEquals(
				"{\"result\":{\"logoutUrl\":\"logoutUrl\",\"loginResponse\":\"LOGGED_IN_AND_REGISTERED\",\"signInUrl\":\"\",\"player\":{\"email\":{\"address\":\"address\"},\"nickname\":\"nickname\"}}}",
				actual);
	}

	@Test
	public void testClearAllScores() {
		CommandInstance command = CommandInstanceFactory.clearAllScores(player.email);
		Assert.assertEquals(
				"{\"oldScores\":[{\"playerEmail\":{\"address\":\"address\"},\"score\":1},{\"playerEmail\":{\"address\":\"address2\"},\"score\":0}],\"changedBy\":{\"address\":\"address\"}}",
				command.getCommandSerialized());
	}

	@Test
	public void deserializeCleanAllScores() {
		CommandInstance command = CommandInstanceFactory.clearAllScores(player.email);
		String ser = command.getCommandSerialized();
		PlayerCommand deserialize = new Serializer().deserialize(ser, ClearAllScores.class);
		Assert.assertNotNull(deserialize.getDescription());
	}

	@Test
	public void testPlayerWonOverPlayer() {
		GameResultDto result = GameResultDto.createEmptyGameResult();
		result.setScores[0].gameWinnerScore = 11;
		result.setScores[0].gameLoserScore = 5;
		PlayerCommand command = new PlayerWonOverPlayer(player.email, player2.email, result, player.email);
		String actual = new Serializer().deepSerialize(command);
		Assert.assertEquals(
				"{\"scores\":{\"oldWinnerScore\":1,\"oldLoserScore\":0,\"newWinnerScore\":2,\"newLoserScore\":0},\"winnerEmail\":{\"address\":\"address\"},\"loserEmail\":{\"address\":\"address2\"},\"changedBy\":{\"address\":\"address\"}}",
				actual);
	}

	@Test
	public void testRegisterPlayer() {
		PlayerCommand command = new RegisterPlayer(player.email);
		String actual = new Serializer().deepSerialize(command);
		Assert.assertEquals("{\"email\":{\"address\":\"address\"}}", actual);
	}

	@Test
	public void testSendMessage() {
		PlayerCommand command = new SendMessage(player.email, "message");
		String actual = new Serializer().deepSerialize(command);
		Assert.assertEquals("{\"email\":{\"address\":\"address\"},\"message\":\"message\"}", actual);
	}

	@Test
	public void testUndoPlayerCommand() {
		CommandInstance playerWonOverPlayer = CommandInstanceFactory.playerWonOverPlayer(player.email, player2.email,
				player.email);
		playerWonOverPlayer.getCommand().execute();
		CommandInstanceRepository.get().create(playerWonOverPlayer);
		PlayerCommand command = new UndoPlayerCommand(player.email);
		command.execute();
		String actual = new Serializer().deepSerialize(command);
		Assert.assertEquals(
				"{\"player\":{\"address\":\"address\"},\"result\":\"SUCCESS\",\"commandUndoDescription\":\"address vann över address2 och ökade sina poäng från 1 till 2. Förloraren fick 0 poäng\"}",
				actual);
	}

}
