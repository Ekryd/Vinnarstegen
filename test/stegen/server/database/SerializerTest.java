package stegen.server.database;

import org.junit.*;

import stegen.server.command.*;
import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class SerializerTest {
	private static final EmailAddressDto email = new EmailAddressDto("address");
	private static final EmailAddressDto email2 = new EmailAddressDto("address2");
	private static final PlayerDto player = new PlayerDto(email, "nickname");
	private static final PlayerDto player2 = new PlayerDto(email2, "nickname2");

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());
	private final Serializer serializer = new Serializer();

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
	public void serializeChangeNickname() {
		PlayerCommand command = new ChangeNickname(player, "nicknameNew");
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"email\":{\"address\":\"address\"},\"newNickname\":\"nicknameNew\",\"oldNickname\":\"nickname\"}",
				actual);
	}

	@Test
	public void deserializeChangeNickname() {
		PlayerCommand deserialize = serializer.deserialize(
				"{\"email\":{\"address\":\"address\"},\"newNickname\":\"nicknameNew\",\"oldNickname\":\"nickname\"}",
				ChangeNickname.class);
		Assert.assertEquals("address bytte alias från nickname till nicknameNew", deserialize.getDescription());
	}

	@Test
	public void serializeCheckLoginStatus() {
		LoginDataDto result = LoginDataDto.userIsLoggedInAndRegistered(player, "logoutUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", result);
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"result\":{\"logoutUrl\":\"logoutUrl\",\"loginResponse\":\"LOGGED_IN_AND_REGISTERED\",\"signInUrl\":\"\",\"player\":{\"email\":{\"address\":\"address\"},\"nickname\":\"nickname\"}}}",
				actual);
	}

	@Test
	public void deserializeCheckLoginStatus() {
		PlayerCommand deserialize = serializer
				.deserialize(
						"{\"result\":{\"logoutUrl\":\"logoutUrl\",\"loginResponse\":\"LOGGED_IN_GMAIL\",\"signInUrl\":\"\",\"player\":{\"email\":{\"address\":\"address\"},\"nickname\":\"nickname\"}}}",
						CheckLoginStatus.class);
		Assert.assertEquals("nickname tittade till applikationen, men var inte registrerad",
				deserialize.getDescription());
	}

	@Test
	public void serializeClearAllScores() {
		CommandInstance command = CommandInstanceFactory.clearAllScores(player.email);
		Assert.assertEquals(
				"{\"oldScores\":[{\"playerEmail\":{\"address\":\"address\"},\"score\":1},{\"playerEmail\":{\"address\":\"address2\"},\"score\":0}],\"changedBy\":{\"address\":\"address\"}}",
				command.getCommandSerialized());
	}

	@Test
	public void deserializeCleanAllScores() throws Exception {
		PlayerRepository playerRepository = PlayerRepository.get();
		CommandInstance command = CommandInstanceFactory.clearAllScores(player.email);
		command.getCommand().execute();
		Assert.assertEquals(0, playerRepository.getPlayer(email).getScore());

		PlayerCommand deserialize = serializer
				.deserialize(
						"{\"oldScores\":[{\"playerEmail\":{\"address\":\"address\"},\"score\":1},{\"playerEmail\":{\"address\":\"address2\"},\"score\":0}],\"changedBy\":{\"address\":\"address\"}}",
						ClearAllScores.class);
		Assert.assertEquals("Rensade alla poäng", deserialize.getDescription());
		deserialize.undo();
		Assert.assertEquals(1, playerRepository.getPlayer(email).getScore());
	}

	@Test
	public void serializePlayerWonOverPlayer() {
		GameResultDto result = GameResultDto.createEmptyGameResult();
		result.setScores[0].gameWinnerScore = 11;
		result.setScores[0].gameLoserScore = 5;
		PlayerCommand command = new PlayerWonOverPlayer(player.email, player2.email, result, player.email);
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"scores\":{\"oldWinnerScore\":1,\"oldLoserScore\":0,\"newWinnerScore\":2,\"newLoserScore\":0},\"winnerEmail\":{\"address\":\"address\"},\"loserEmail\":{\"address\":\"address2\"},\"changedBy\":{\"address\":\"address\"}}",
				actual);
	}

	@Test
	public void deserializePlayerWonOverPlayer() {
		PlayerCommand deserialize = serializer
				.deserialize(
						"{\"scores\":{\"oldWinnerScore\":1,\"oldLoserScore\":0,\"newWinnerScore\":2,\"newLoserScore\":0},\"winnerEmail\":{\"address\":\"address\"},\"loserEmail\":{\"address\":\"address2\"},\"changedBy\":{\"address\":\"address\"}}",
						PlayerWonOverPlayer.class);
		Assert.assertEquals("address vann över address2 och ökade sina poäng från 1 till 2. Förloraren fick 0 poäng",
				deserialize.getDescription());
	}

	@Test
	public void serializeRegisterPlayer() {
		PlayerCommand command = new RegisterPlayer(player.email);
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals("{\"email\":{\"address\":\"address\"}}", actual);
	}

	@Test
	public void deserializeRegisterPlayer() {
		PlayerCommand deserialize = serializer.deserialize("{\"email\":{\"address\":\"address\"}}",
				RegisterPlayer.class);
		Assert.assertEquals("Registrerade address", deserialize.getDescription());
	}

	@Test
	public void serializeSendMessage() {
		PlayerCommand command = new SendMessage(player.email, "message");
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals("{\"email\":{\"address\":\"address\"},\"message\":\"message\"}", actual);
	}

	@Test
	public void deserializeSendMessage() {
		PlayerCommand deserialize = serializer.deserialize(
				"{\"email\":{\"address\":\"address\"},\"message\":\"message\"}", SendMessage.class);
		Assert.assertEquals("message", deserialize.getDescription());
	}

	@Test
	public void serializeUndoPlayerCommand() {
		CommandInstance playerWonOverPlayer = CommandInstanceFactory.playerWonOverPlayer(player.email, player2.email,
				player.email);
		playerWonOverPlayer.getCommand().execute();
		CommandInstanceRepository.get().create(playerWonOverPlayer);
		PlayerCommand command = new UndoPlayerCommand(player.email);
		command.execute();
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"player\":{\"address\":\"address\"},\"result\":\"SUCCESS\",\"commandUndoDescription\":\"address vann över address2 och ökade sina poäng från 1 till 2. Förloraren fick 0 poäng\"}",
				actual);
	}

	@Test
	public void deserializeUndoPlayerCommand() {
		CommandInstance playerWonOverPlayer = CommandInstanceFactory.playerWonOverPlayer(player.email, player2.email,
				player.email);
		playerWonOverPlayer.getCommand().execute();
		CommandInstanceRepository.get().create(playerWonOverPlayer);
		PlayerCommand command = new UndoPlayerCommand(player.email);
		command.execute();

		PlayerCommand deserialize = serializer
				.deserialize(
						"{\"player\":{\"address\":\"address\"},\"result\":\"SUCCESS\",\"commandUndoDescription\":\"address vann över address2 och ökade sina poäng från 1 till 2. Förloraren fick 0 poäng\"}",
						UndoPlayerCommand.class);
		Assert.assertEquals(
				"Ångrade address vann över address2 och ökade sina poäng från 1 till 2. Förloraren fick 0 poäng",
				deserialize.getDescription());
	}

}
