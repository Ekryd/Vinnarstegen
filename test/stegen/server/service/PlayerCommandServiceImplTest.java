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
	}

	@Test
	public void testGetSendMessageCommandStackEmpty() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getSendMessageCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetGameResultCommandStack() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getGameResultCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetLoginStatusCommandStack() {
		List<PlayerCommandDto> commandStack = playerCommandServiceImpl.getLoginStatusCommandStack(10);
		assertEquals(0, commandStack.size());
	}

	@Test
	public void testGetUndoCommand() {
		PlayerCommandDto undoCommand = playerCommandServiceImpl.getUndoCommand();
		assertEquals(null, undoCommand);
	}

}
