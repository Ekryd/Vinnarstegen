package stegen.server.database;

import org.junit.*;

import stegen.server.command.*;
import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class SerializeDatabaseEntitiesTest {
	private static final EmailAddressDto email = new EmailAddressDto("address");
	private static final EmailAddressDto email2 = new EmailAddressDto("address2");
	private static final PlayerDto player = new PlayerDto(email, "nickname");
	private static final PlayerDto player2 = new PlayerDto(email2, "nickname2");

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());
	private final Serializer serializer = new Serializer();
	private CommandInstanceFactory databaseTestObjectFactory;

	@Before
	public void setUp() {
		helper.setUp();
		databaseTestObjectFactory = new CommandInstanceFactory();
		databaseTestObjectFactory.createPlayer(player.email);
		databaseTestObjectFactory.createPlayer(player2.email);
		databaseTestObjectFactory.addPlayerWonOverPlayer(player.email, player2.email, player.email).getCommand()
				.execute();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void serializeChangeNickname() {
		PlayerCommand command = new ChangeNickname(player.email, "nicknameNew");
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"email\":{\"address\":\"address\"},\"newNickname\":\"nicknameNew\",\"oldNickname\":\"address\"}",
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
		LoginDataDto loginData = LoginDataDto.userIsLoggedInAndRegistered(player, "logoutUrl");
		PlayerCommand command = CheckLoginStatus.createForTest("requestUri", loginData);
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
		CommandInstance command = databaseTestObjectFactory.addClearAllScores(player.email);
		Assert.assertEquals(
				"{\"oldScores\":[{\"playerEmail\":{\"address\":\"address\"},\"score\":4},{\"playerEmail\":{\"address\":\"address2\"},\"score\":1}],\"changedBy\":{\"address\":\"address\"}}",
				command.getCommandSerialized());
	}

	@Test
	public void deserializeCleanAllScores() throws Exception {
		PlayerRepository playerRepository = PlayerRepository.get();
		CommandInstance command = databaseTestObjectFactory.addClearAllScores(player.email);
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
		GameResultDto result = LoginDataDtoFactory.createGameResult41();
		PlayerCommand command = new PlayerWonOverPlayer(player.email, player2.email, result, player.email);
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"scores\":{\"oldWinnerScore\":4,\"oldLoserScore\":1,\"newWinnerScore\":7,\"newLoserScore\":3},\"winnerEmail\":{\"address\":\"address\"},\"loserEmail\":{\"address\":\"address2\"},\"changedBy\":{\"address\":\"address\"}}",
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
		CommandInstance playerWonOverPlayer = databaseTestObjectFactory.addPlayerWonOverPlayer(player.email,
				player2.email, player.email);
		playerWonOverPlayer.getCommand().execute();
		CommandInstanceRepository.get().create(playerWonOverPlayer);
		PlayerCommand command = new UndoPlayerCommand(player.email);
		command.execute();
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"player\":{\"address\":\"address\"},\"result\":\"SUCCESS\",\"commandUndoDescription\":\"address vann över address2 och ökade sina poäng från 4 till 8. Förloraren fick 1 poäng\"}",
				actual);
	}

	@Test
	public void deserializeUndoPlayerCommand() {
		CommandInstance playerWonOverPlayer = databaseTestObjectFactory.addPlayerWonOverPlayer(player.email,
				player2.email, player.email);
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

	@Test
	public void serializeChallengeCommand() {
		ChallengeMessageDto message = new ChallengeMessageDto(new EmailAddressDto("jimjoh71@gmail.com"),
				new EmailAddressDto("martin@gotblad.com"), "en svag skrotnisse", "body", "subject");
		PlayerCommand command = new Challenge(message);
		command.execute();
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals(
				"{\"insult\":\"en svag skrotnisse\",\"challenger\":{\"address\":\"jimjoh71@gmail.com\"},\"challengee\":{\"address\":\"martin@gotblad.com\"}}",
				actual);
	}

	@Test
	public void deserializeChallengeCommand() {
		PlayerCommand deserialize = serializer
				.deserialize(
						"{\"insult\":\"en svag skrotnisse\",\"challenger\":{\"address\":\"jimjoh71@gmail.com\"},\"challengee\":{\"address\":\"martin@gotblad.com\"}}",
						Challenge.class);
		Assert.assertEquals(
				"jimjoh71@gmail.com kallade martin@gotblad.com för en svag skrotnisse och utmanade därmed honom till duell",
				deserialize.getDescription());
	}

	@Test
	public void serializeRemovePlayerCommand() {
		databaseTestObjectFactory.addChangeNickname().getCommand().execute();
		PlayerCommand command = new RemovePlayer(player.email);
		command.execute();
		String actual = serializer.deepSerialize(command);
		Assert.assertEquals("{\"email\":{\"address\":\"address\"},\"nickname\":\"nickname\"}", actual);
	}

	@Test
	public void deserializeRemovePlayerCommand() {
		PlayerCommand deserialize = serializer.deserialize(
				"{\"email\":{\"address\":\"address\"},\"nickname\":\"nickname\"}", RemovePlayer.class);
		Assert.assertEquals("Tog bort spelare nickname, med email address", deserialize.getDescription());
	}

}
