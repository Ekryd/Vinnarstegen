package stegen.server.service;

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

}
