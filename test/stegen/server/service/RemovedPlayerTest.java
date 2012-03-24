package stegen.server.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.rules.*;

import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class RemovedPlayerTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig(), new LocalUserServiceTestConfig());
	private PlayerServiceImpl playerServiceImpl;
	private PlayerCommandServiceImpl playerCommandServiceImpl;
	private ScoreServiceImpl scoreServiceImpl;
	private final PlayerDto player = LoginDataDtoFactory.createLoginData().player;
	private final PlayerDto anotherPlayer = LoginDataDtoFactory.createOtherLoginData().player;
	private final String nickname = "alias";

	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Before
	public void setUp() {
		helper.setEnvIsLoggedIn(true).setEnvEmail(player.email.address).setEnvAuthDomain("gmail.com");
		helper.setUp();
		playerServiceImpl = new PlayerServiceImpl();
		playerCommandServiceImpl = new PlayerCommandServiceImpl();
		scoreServiceImpl = new ScoreServiceImpl();
		createAndRemovePlayer();
	}

	private void createAndRemovePlayer() {
		playerServiceImpl.registerPlayer(anotherPlayer.email);
		playerServiceImpl.registerPlayer(player.email);

		scoreServiceImpl.playerWonOverPlayer(player, anotherPlayer, LoginDataDtoFactory.createGameResult30(), player);
		playerCommandServiceImpl.undoPlayerCommand(player);
		playerServiceImpl.sendMessage(player, "message");
		scoreServiceImpl.playerWonOverPlayer(player, anotherPlayer, LoginDataDtoFactory.createGameResult41(), player);
		scoreServiceImpl.clearAllScores(player);
		playerServiceImpl.getUserLoginStatus("requestUri");
		playerServiceImpl.changeNickname(player, nickname);
		scoreServiceImpl.challengePlayer(getChallengeMessage());

		removePlayerAndTimeoutCache();
	}

	private void removePlayerAndTimeoutCache() {
		playerServiceImpl.removePlayer(player.email);
		NicknameService.get().clearCache();
	}

	private ChallengeMessageDto getChallengeMessage() {
		return new ChallengeMessageDto(player.email, anotherPlayer.email, "body", "subject");
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void shouldBeVisibleInMiscPlayerCommandStack() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(1);
		assertThat(commands, hasSize(1));
		collector.checkThat(commands.get(0).description, is("Tog bort spelare alias, med email address"));
		collector.checkThat(commands.get(0).performedDateTime, notNullValue());
		collector.checkThat(commands.get(0).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldNotBeAbleToLogin() {
		LoginDataDto userLoginStatus = playerServiceImpl.getUserLoginStatus("requestUri");
		assertThat(userLoginStatus.loginResponse, is(LoginResult.LOGGED_IN_GMAIL));
		assertThat(userLoginStatus.loginResponse, is(not(LoginResult.LOGGED_IN_AND_REGISTERED)));
	}

	@Test
	public void shouldNotBeVisibleInPlayerScoreList() {
		List<PlayerScoreDto> playerScoreList = scoreServiceImpl.getPlayerScoreList(anotherPlayer.email);
		assertThat(playerScoreList, hasSize(1));
		assertThat(playerScoreList.get(0).player.email.address, is(anotherPlayer.email.address));
	}

	@Test
	public void shouldKeepTheirAlias() {
		String nickname = NicknameService.get().getNickname(player.email);
		assertThat(nickname, is(nickname));
	}

	@Test
	public void shouldBeVisibleInChallenges() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(2);
		assertThat(commands, hasSize(2));
		collector.checkThat(commands.get(1).description,
				is("address utmanade address2."));
		collector.checkThat(commands.get(1).performedDateTime, notNullValue());
		collector.checkThat(commands.get(1).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInChangedNicknames() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(3);
		assertThat(commands, hasSize(3));
		collector.checkThat(commands.get(2).description, is("address bytte alias från address till alias"));
		collector.checkThat(commands.get(2).performedDateTime, notNullValue());
		collector.checkThat(commands.get(2).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInLoginStatuses() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getLoginStatusCommandStack(1);
		assertThat(commands, hasSize(1));
		collector.checkThat(commands.get(0).description, is("Loggade just in i applikationen"));
		collector.checkThat(commands.get(0).performedDateTime, notNullValue());
		collector.checkThat(commands.get(0).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInClearAllScores() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getGameResultCommandStack(1);
		assertThat(commands, hasSize(1));
		collector.checkThat(commands.get(0).description, is("Rensade alla poäng"));
		collector.checkThat(commands.get(0).performedDateTime, notNullValue());
		collector.checkThat(commands.get(0).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInPlayerWonOverPlayer() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getGameResultCommandStack(2);
		assertThat(commands, hasSize(2));
		collector.checkThat(commands.get(1).description,
				is("address vann över address2 och ökade sina poäng från 0 till 3. Förloraren fick 2 poäng"));
		collector.checkThat(commands.get(1).performedDateTime, notNullValue());
		collector.checkThat(commands.get(1).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInRemovePlayer() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(1);
		assertThat(commands, hasSize(1));
		collector.checkThat(commands.get(0).description, is("Tog bort spelare alias, med email address"));
		collector.checkThat(commands.get(0).performedDateTime, notNullValue());
		collector.checkThat(commands.get(0).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInSendMessage() {
		int stackSize = 1;
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getSendMessageCommandStack(stackSize);
		assertThat(commands, hasSize(stackSize));
		collector.checkThat(commands.get(stackSize - 1).description, is("message"));
		collector.checkThat(commands.get(stackSize - 1).performedDateTime, notNullValue());
		collector.checkThat(commands.get(stackSize - 1).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInUndoCommand() {
		int stackSize = 4;
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(stackSize);
		assertThat(commands, hasSize(stackSize));
		collector.checkThat(commands.get(stackSize - 1).description,
				is("Ångrade address vann över address2 och ökade sina poäng från 0 till 5. Förloraren fick 0 poäng"));
		collector.checkThat(commands.get(stackSize - 1).performedDateTime, notNullValue());
		collector.checkThat(commands.get(stackSize - 1).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleAsChangedByInPlayerScoreList() {
		List<PlayerScoreDto> playerScoreList = scoreServiceImpl.getPlayerScoreList(anotherPlayer.email);
		assertThat(playerScoreList, hasSize(1));
		assertThat(playerScoreList.get(0).changedBy.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInRegisterPlayer() {
		int stackSize = 5;
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(stackSize);
		assertThat(commands, hasSize(stackSize));
		collector.checkThat(commands.get(stackSize - 1).description, is("Registrerade address"));
		collector.checkThat(commands.get(stackSize - 1).performedDateTime, notNullValue());
		collector.checkThat(commands.get(stackSize - 1).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldNotBeUndoable() {
		PlayerCommandDto undoCommand = playerCommandServiceImpl.getUndoCommand();
		collector.checkThat(undoCommand.description, is("Rensade alla poäng"));
		collector.checkThat(undoCommand.performedDateTime, notNullValue());
		collector.checkThat(undoCommand.player.email.address, is(player.email.address));
	}

}
