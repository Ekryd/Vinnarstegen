package stegen.server.database;

import java.util.*;

import javax.jdo.*;

import stegen.server.command.*;

public class CommandInstanceRepository {
	private static CommandInstanceRepository instance = new CommandInstanceRepository();

	private CommandInstanceRepository() {}

	public static CommandInstanceRepository get() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public CommandInstance getLatestUndoable() {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(CommandInstance.class);
			query.setRange(0, 1);
			query.setFilter("undoable == undoableParam");
			query.setOrdering("dateTime desc");
			query.declareParameters("boolean undoableParam");
			List<CommandInstance> commands = (List<CommandInstance>) query.execute(true);
			if (commands.size() == 0) {
				return CommandInstance.NOT_FOUND;
			} else {
				return commands.get(0);
			}
		} finally {
			pm.close();
		}
	}

	public void delete(CommandInstance command) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			CommandInstance commandToDelete = pm.getObjectById(CommandInstance.class, command.getId());
			pm.deletePersistent(commandToDelete);
		} finally {
			pm.close();
		}
	}

	public void create(CommandInstance commandInstanceToStore) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			// Query query = pm.newQuery(CommandInstance.class);
			// query.setRange(49, 60);
			// List<CommandInstance> commands = (List<CommandInstance>)
			// query.execute();
			// pm.deletePersistentAll(commands);
			pm.makePersistent(commandInstanceToStore);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<CommandInstance> getPlayerCommandStack(int maxDepth) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(CommandInstance.class);
			query.setOrdering("dateTime desc");
			query.setRange(0, maxDepth);
			List<CommandInstance> commands = (List<CommandInstance>) query.execute();
			// To prevent lazy load exception
			commands.size();
			return commands;
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<CommandInstance> getPlayerCommandStack(int maxDepth,
			Class<? extends PlayerCommand>... filterByCommandType) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(CommandInstance.class);
			query.setOrdering("dateTime desc");
			query.setFilter("commandTypes.contains(this.commandClassName)");
			query.declareParameters("java.util.List commandTypes");
			query.setRange(0, maxDepth);
			List<CommandInstance> commands = (List<CommandInstance>) query.execute(convertTypes(filterByCommandType));
			// To prevent lazy load exception
			commands.size();
			return commands;
		} finally {
			pm.close();
		}
	}

	private List<String> convertTypes(Class<? extends PlayerCommand>[] filterByCommandType) {
		List<String> returnValue = new ArrayList<String>();
		for (Class<? extends PlayerCommand> clazz : filterByCommandType) {
			returnValue.add(clazz.getName());
		}
		return returnValue;
	}
}
