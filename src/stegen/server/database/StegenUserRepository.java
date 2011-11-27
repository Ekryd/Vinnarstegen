package stegen.server.database;

import javax.jdo.*;

public class StegenUserRepository {
	private static StegenUserRepository instance = new StegenUserRepository();

	private StegenUserRepository() {}

	public static StegenUserRepository get() {
		return instance;
	}

	public void updateOrAddStegenUserToDatabase(String emailString, String nickname) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			pm.makePersistent(StegenUser.createUser(emailString, nickname));
		} finally {
			pm.close();
		}
	}

	public StegenUser getUserInDatabase(String emailString) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			return pm.getObjectById(StegenUser.class, emailString);
		} finally {
			pm.close();
		}
	}

	public void removePlayer(String emailString) {
		if (isUserInDatabase(emailString)) {
			PersistenceManager pm = Pmf.getPersistenceManager();
			try {
				StegenUser stegenUser = pm.getObjectById(StegenUser.class, emailString);
				pm.deletePersistent(stegenUser);
			} finally {
				pm.close();
			}
		}
	}

	public boolean isUserInDatabase(String emailString) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(StegenUser.class, "select emailString");
			query.setFilter("emailString == emailParam");
			query.declareParameters("String emailParam");
			query.setUnique(true);
			return query.execute(emailString) != null;
		} finally {
			pm.close();
		}
	}

}
