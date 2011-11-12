package stegen.server.service;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import stegen.server.database.*;
import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

public class PlayerCommandServiceImplTest {
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalTaskQueueTestConfig(),
			new LocalDatastoreServiceTestConfig());
	private PlayerCommandServiceImpl playerCommandServiceImpl;
	private CommandInstanceFactory databaseTestObjectFactory;

	@Before
	public void setUp() {
		helper.setUp();
		playerCommandServiceImpl = new PlayerCommandServiceImpl();
		databaseTestObjectFactory = new CommandInstanceFactory();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testGetMiscPlayerCommandStackEmpty() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getMiscPlayerCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetMiscPlayerCommandStackNotEmpty() {
		databaseTestObjectFactory.createPlayer();
		databaseTestObjectFactory.addChangeNickname();

		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getMiscPlayerCommandStack(10);
		assertEquals(1, commandStack.size());
		assertEquals("address bytte alias från address till nickname", commandStack.get(0).description);
	}

	@Test
	public void testGetSendMessageCommandStackEmpty() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getSendMessageCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetSendMessageCommandStackNotEmpty() {
		databaseTestObjectFactory.createPlayer();
		databaseTestObjectFactory.addSendMessage("message");

		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getSendMessageCommandStack(10);
		assertEquals(1, commandStack.size());
		assertEquals("message", commandStack.get(0).description);
	}

	@Test
	public void testGetGameResultCommandStackEmpty() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getGameResultCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetGameResultCommandStackNotEmpty() {
		EmailAddressDto playerEmail1 = new EmailAddressDto("winner");
		EmailAddressDto playerEmail2 = new EmailAddressDto("loser");
		databaseTestObjectFactory.createPlayer(playerEmail1);
		databaseTestObjectFactory.createPlayer(playerEmail2);
		databaseTestObjectFactory.addPlayerWonOverPlayer(playerEmail1, playerEmail2, playerEmail1);

		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getGameResultCommandStack(10);
		assertEquals(1, commandStack.size());
		assertEquals("winner vann över loser och ökade sina poäng från 0 till 4. Förloraren fick 1 poäng",
				commandStack.get(0).description);
	}

	@Test
	public void testGetLoginStatusCommandStackEmpty() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getLoginStatusCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetLoginStatusCommandStackNotEmpty() {
		databaseTestObjectFactory.createPlayer();
		databaseTestObjectFactory.addUserIsLoggedInAndRegistered();

		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getLoginStatusCommandStack(10);
		assertEquals(1, commandStack.size());
		assertEquals("Loggade just in i applikationen", commandStack.get(0).description);
	}

	@Test
	public void testGetUndoCommandEmpty() {
		PlayerCommandDto undoCommand = playerCommandServiceImpl.getUndoCommand();
		assertEquals(null, undoCommand);
	}

	@Test
	public void testGetUndoCommandNotEmpty() {
		EmailAddressDto playerEmail1 = new EmailAddressDto("winner");
		EmailAddressDto playerEmail2 = new EmailAddressDto("loser");
		databaseTestObjectFactory.createPlayer(playerEmail1);
		databaseTestObjectFactory.createPlayer(playerEmail2);
		databaseTestObjectFactory.addPlayerWonOverPlayer(playerEmail1, playerEmail2, playerEmail1);
		databaseTestObjectFactory.addPlayerWonOverPlayer(playerEmail1, playerEmail2, playerEmail1);

		PlayerCommandDto undoCommand = playerCommandServiceImpl.getUndoCommand();
		assertNotNull(null, undoCommand);
		assertEquals("winner vann över loser och ökade sina poäng från 0 till 4. Förloraren fick 1 poäng",
				undoCommand.description);
	}

	@Test
	public void testGetLatestCommandIdEmpty() {
		long latestCommandId = playerCommandServiceImpl.getLatestCommandId();
		assertEquals(0, latestCommandId);
	}

	@Test
	public void testGetLatestCommandIdNotEmpty() {
		EmailAddressDto playerEmail1 = new EmailAddressDto("winner");
		EmailAddressDto playerEmail2 = new EmailAddressDto("loser");
		databaseTestObjectFactory.createPlayer(playerEmail1);
		databaseTestObjectFactory.createPlayer(playerEmail2);
		databaseTestObjectFactory.addPlayerWonOverPlayer(playerEmail1, playerEmail2, playerEmail1);
		databaseTestObjectFactory.addPlayerWonOverPlayer(playerEmail1, playerEmail2, playerEmail1);

		long latestCommandId = playerCommandServiceImpl.getLatestCommandId();
		assertEquals(true, latestCommandId != 0);
	}

}
