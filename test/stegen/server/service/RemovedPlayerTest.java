package stegen.server.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.rules.*;

import stegen.server.database.*;
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
		playerServiceImpl.changeNickname(player, nickname);
		scoreServiceImpl.challengePlayer(getChallengeMessage());
		playerServiceImpl.removePlayer(player.email);
		StegenUserRepository.get().clearCache();
	}

	private ChallengeMessageDto getChallengeMessage() {
		return new ChallengeMessageDto(player.email, anotherPlayer.email, "insult", "body", "subject");
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
		List<PlayerScoreDto> playerScoreList = scoreServiceImpl.getPlayerScoreList();
		assertThat(playerScoreList, hasSize(1));
		assertThat(playerScoreList.get(0).player.email.address, is(anotherPlayer.email.address));
	}

	@Test
	public void shouldKeepTheirAlias() {
		String nickname = StegenUserRepository.get().getOrCreateNickname(player.email);
		assertThat(nickname, is(nickname));
	}

	@Test
	public void shouldBeVisibleInChallenges() {
		List<PlayerCommandDto> commands = playerCommandServiceImpl.getMiscPlayerCommandStack(2);
		assertThat(commands, hasSize(2));
		collector.checkThat(commands.get(1).description,
				is("alias kallade address2 för insult och utmanade därmed honom till duell"));
		collector.checkThat(commands.get(1).performedDateTime, notNullValue());
		collector.checkThat(commands.get(1).player.email.address, is(player.email.address));
	}

	@Test
	public void shouldBeVisibleInChangedNicknames() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInLoginStatuses() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInClearAllScores() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInPlayerWonOverPlayer() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInRegisterPlayer() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInRemovePlayer() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInSendMessage() {
		// TODO
	}

	@Test
	public void shouldBeVisibleInUndoCommand() {
		// TODO
	}

	@Test
	public void shouldBeVisibleAsChangedByInPlayerScoreList() {
		// TODO
	}

	@Test
	public void shouldBeUndoable() {
		// TODO
	}

}
