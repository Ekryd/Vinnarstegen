package stegen.server.service;

import static org.junit.Assert.*;
import static stegen.shared.LoginDataDtoFactory.*;

import java.util.*;

import org.junit.*;

import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class ScoreServiceImplTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());
	private ScoreServiceImpl scoreServiceImpl;
	private PlayerServiceImpl playerServiceImpl;

	@Before
	public void setUp() {
		helper.setUp();
		scoreServiceImpl = new ScoreServiceImpl();
		playerServiceImpl = new PlayerServiceImpl();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testGetPlayerScoreList() {
		for (int i = 0; i < 20; i++) {
			playerServiceImpl.registerPlayer(new EmailAddressDto("address" + i));
		}
		List<PlayerScoreDto> playerScoreList = scoreServiceImpl.getPlayerScoreList();
		Assert.assertEquals(20, playerScoreList.size());
	}

	@Test
	public void testGetPlayerScoreListSorting() throws InterruptedException {
		playerServiceImpl.registerPlayer(new EmailAddressDto("address1"));
		Thread.sleep(50);
		playerServiceImpl.registerPlayer(new EmailAddressDto("address2"));
		Thread.sleep(50);
		playerServiceImpl.registerPlayer(new EmailAddressDto("address3"));

		List<PlayerScoreDto> playerScoreList = scoreServiceImpl.getPlayerScoreList();
		assertEquals(3, playerScoreList.size());
		assertEquals("address3", playerScoreList.get(0).player.email.address);
		assertEquals("address2", playerScoreList.get(1).player.email.address);
		assertEquals("address1", playerScoreList.get(2).player.email.address);

		GameResultDto result = createGameResult30();
		PlayerDto player1 = createPlayerDto("address1");
		PlayerDto player2 = createPlayerDto("address2");
		PlayerDto player3 = createPlayerDto("address3");
		scoreServiceImpl.playerWonOverPlayer(player1, player3, result, player3);
		scoreServiceImpl.playerWonOverPlayer(player2, player3, result, player3);

		playerScoreList = scoreServiceImpl.getPlayerScoreList();
		assertEquals(3, playerScoreList.size());
		assertEquals(playerScoreList.get(0).score, playerScoreList.get(1).score);

		// Latest change first
		assertEquals("address2", playerScoreList.get(0).player.email.address);
		assertEquals("address1", playerScoreList.get(1).player.email.address);
	}
	
	
}
