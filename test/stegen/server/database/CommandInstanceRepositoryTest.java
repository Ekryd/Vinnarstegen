package stegen.server.database;

import java.util.*;

import org.junit.*;

import stegen.client.dto.*;

import com.google.appengine.tools.development.testing.*;

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
			Assert.assertTrue(commandInstance.getPlayerUndoCommand().description.length() != 0);
		}
	}

}
