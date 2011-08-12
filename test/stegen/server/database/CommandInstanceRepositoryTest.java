package stegen.server.database;

import java.util.*;

import org.junit.*;

import stegen.server.command.*;
import stegen.shared.*;

import com.google.appengine.tools.development.testing.*;

@SuppressWarnings("unchecked")
public class CommandInstanceRepositoryTest {
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
	public void testGetLatestUndoableEmpty() {
		CommandInstance latestUndoable = CommandInstanceRepository.get().getLatestUndoable();
		Assert.assertNotNull(latestUndoable);
	}

	@Test
	public void testGetLatestUndoableDescription() {
		CommandInstance latestUndoable = CommandInstanceRepository.get().getLatestUndoable();
		Assert.assertNotNull(latestUndoable);
	}

	@Test
	public void testCreateAndDelete() {
		CommandInstanceRepository commandInstanceRepository = CommandInstanceRepository.get();
		EmailAddressDto playerEmail = new EmailAddressDto("address");
		commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));

		CommandInstance command = commandInstanceRepository.getLatestUndoable();

		command.undo();
		commandInstanceRepository.delete(command);
	}

	@Test
	public void testGetPlayerCommandStack() {
		PlayerRepository playerRepository = PlayerRepository.get();
		EmailAddressDto playerEmail = new EmailAddressDto("address");
		Player player = Player.createPlayer(playerEmail);
		playerRepository.create(player);
		CommandInstanceRepository commandInstanceRepository = CommandInstanceRepository.get();
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsNotLoggedInCommand());
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsNotRegistered());
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsLoggedInAndRegistered());

		commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
		List<CommandInstance> stack = commandInstanceRepository.getPlayerCommandStack(10);
		Assert.assertNotNull(stack);
		Assert.assertEquals(4, stack.size());
		for (CommandInstance commandInstance : stack) {
			Assert.assertTrue(commandInstance.getCommand() != null);
			Assert.assertTrue(commandInstance.createPlayerCommandDto().description.length() != 0);
		}
	}

	@Test
	public void testGetPlayerCommandStackWithSendMessaget() {
		PlayerRepository playerRepository = PlayerRepository.get();
		EmailAddressDto playerEmail = new EmailAddressDto("address");
		Player player = Player.createPlayer(playerEmail);
		playerRepository.create(player);
		CommandInstanceRepository commandInstanceRepository = CommandInstanceRepository.get();
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsNotLoggedInCommand());
		commandInstanceRepository.create(CommandInstanceFactory.sendMessage("hepp"));
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsNotRegistered());
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsLoggedInAndRegistered());
		commandInstanceRepository.create(CommandInstanceFactory.sendMessage("hopp"));

		commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
		List<CommandInstance> stack = commandInstanceRepository.getPlayerCommandStack(10, SendMessage.class);
		Assert.assertNotNull(stack);
		Assert.assertEquals(2, stack.size());
		for (CommandInstance commandInstance : stack) {
			Assert.assertTrue(commandInstance.getCommand() != null);
			Assert.assertTrue(commandInstance.createPlayerCommandDto().description.length() != 0);
		}
	}

	@Test
	public void testGetPlayerCommandStackWithSendMessagetAndClearAllScores() {
		PlayerRepository playerRepository = PlayerRepository.get();
		EmailAddressDto playerEmail = new EmailAddressDto("address");
		Player player = Player.createPlayer(playerEmail);
		playerRepository.create(player);
		CommandInstanceRepository commandInstanceRepository = CommandInstanceRepository.get();
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsNotLoggedInCommand());
		commandInstanceRepository.create(CommandInstanceFactory.sendMessage("hepp"));
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsNotRegistered());
		commandInstanceRepository.create(CommandInstanceFactory.getUserIsLoggedInAndRegistered());
		commandInstanceRepository.create(CommandInstanceFactory.sendMessage("hopp"));

		commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
		List<CommandInstance> stack = commandInstanceRepository.getPlayerCommandStack(10, SendMessage.class,
				ClearAllScores.class);
		Assert.assertNotNull(stack);
		Assert.assertEquals(3, stack.size());
		for (CommandInstance commandInstance : stack) {
			Assert.assertTrue(commandInstance.getCommand() != null);
			Assert.assertTrue(commandInstance.createPlayerCommandDto().description.length() != 0);
		}
	}

	// @Test
	// public void testTooManyCommands() {
	// PlayerRepository playerRepository = PlayerRepository.get();
	// EmailAddressDto playerEmail = new EmailAddressDto("address");
	// Player player = Player.createPlayer(playerEmail);
	// playerRepository.create(player);
	// CommandInstanceRepository commandInstanceRepository =
	// CommandInstanceRepository.get();
	//
	// for (int i = 0; i < 49; i++) {
	// commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
	// }
	//
	// Assert.assertEquals(49,
	// commandInstanceRepository.getPlayerCommandStack(100).size());
	// commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
	// Assert.assertEquals(50,
	// commandInstanceRepository.getPlayerCommandStack(100).size());
	// commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
	// Assert.assertEquals(50,
	// commandInstanceRepository.getPlayerCommandStack(100).size());
	// commandInstanceRepository.create(CommandInstanceFactory.clearAllScores(playerEmail));
	// Assert.assertEquals(50,
	// commandInstanceRepository.getPlayerCommandStack(100).size());
	// }

}
