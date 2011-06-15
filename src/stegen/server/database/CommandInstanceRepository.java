package stegen.server.database;

import java.util.*;

import javax.jdo.*;
import javax.jdo.Query;
import javax.jdo.annotations.*;

public class CommandInstanceRepository {
	private static CommandInstanceRepository instance = new CommandInstanceRepository();
	private PersistenceManager pm;

	private CommandInstanceRepository() {}

	public static CommandInstanceRepository get() {
		return instance;
	}

	@SuppressWarnings("unchecked")
	public CommandInstance getLatestUndoable() {
		pm = Pmf.get().getPersistenceManager();
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
			if (!pm.isClosed()) {
				pm.close();
			}
		}
	}

	public void delete(CommandInstance command) {
		pm = Pmf.get().getPersistenceManager();
		try {
			CommandInstance commandToDelete = pm.getObjectById(CommandInstance.class, command.getId());
			pm.deletePersistent(commandToDelete);
		} finally {
			pm.close();
		}
	}

	public void create(CommandInstance commandInstanceToStore) {
		pm = Pmf.get().getPersistenceManager();
		try {
			pm.makePersistent(commandInstanceToStore);
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<CommandInstance> getPlayerCommandStack(int maxDepth) {
		pm = Pmf.get().getPersistenceManager();
		try {
			Query query = pm.newQuery(CommandInstance.class);
			query.setRange(0, maxDepth);
			query.setOrdering("dateTime desc");
			List<CommandInstance> commands = (List<CommandInstance>) query.execute();
			// To prevent lazy load exception
			commands.size();
			return commands;
		} finally {
			if (!pm.isClosed()) {
				pm.close();
			}
		}
	}
}
