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

	public CommandInstance getLatestUndoable() {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(CommandInstance.class);
			query.setRange(0, 1);
			query.setFilter("undoable == undoableParam");
			query.setOrdering("dateTime desc");
			query.declareParameters("boolean undoableParam");

			return getSingleCommandInstance(query);
		} finally {
			pm.close();
		}
	}

	public CommandInstance getLatestCommand() {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(CommandInstance.class);
			query.setRange(0, 1);
			query.setOrdering("dateTime desc");

			return getSingleCommandInstance(query);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private CommandInstance getSingleCommandInstance(Query query) {
		List<CommandInstance> commands = (List<CommandInstance>) query.execute(true);
		if (commands.size() == 0) {
			return CommandInstance.NOT_FOUND;
		} else {
			return commands.get(0);
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

	public List<CommandInstance> getPlayerCommandStack(int maxDepth,
			Class<? extends PlayerCommand>... filterByCommandType) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			return tryGetPlayerCommandStack(maxDepth, pm, filterByCommandType);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<CommandInstance> tryGetPlayerCommandStack(int maxDepth, PersistenceManager pm,
			Class<? extends PlayerCommand>... filterByCommandType) {
		Query query = pm.newQuery(CommandInstance.class);
		query.setOrdering("dateTime desc");
		query.setRange(0, maxDepth);
		List<CommandInstance> commands;
		if (filterByCommandType.length != 0) {
			commands = executeQueryWithPlayerCommandFilter(query, filterByCommandType);
		} else {
			commands = (List<CommandInstance>) query.execute();
		}
		preventLazyLoadException(commands);
		return commands;
	}

	@SuppressWarnings("unchecked")
	private List<CommandInstance> executeQueryWithPlayerCommandFilter(Query query,
			Class<? extends PlayerCommand>... filterByCommandType) {
		query.setFilter("commandTypes.contains(this.commandClassName)");
		query.declareParameters("java.util.List commandTypes");
		return (List<CommandInstance>) query.execute(convertCommandsToClassNames(filterByCommandType));
	}

	private void preventLazyLoadException(List<CommandInstance> commands) {
		commands.size();
	}

	private List<String> convertCommandsToClassNames(Class<? extends PlayerCommand>[] platerCommands) {
		List<String> returnValue = new ArrayList<String>();
		for (Class<? extends PlayerCommand> clazz : platerCommands) {
			returnValue.add(clazz.getName());
		}
		return returnValue;
	}
}
