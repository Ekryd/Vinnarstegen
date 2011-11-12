package stegen.server.database;

import java.util.*;

import javax.jdo.*;

import stegen.shared.*;

public class PlayerRepository {
	private static PlayerRepository instance = new PlayerRepository();

	private PlayerRepository() {}

	public static PlayerRepository get() {
		return instance;
	}

	public static interface Func {
		void exec(Player player);
	}

	public boolean isUserRegistered(Object email) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class, "select emailString");
			query.setFilter("emailString == emailParam");
			query.declareParameters("String emailParam");
			@SuppressWarnings("unchecked")
			List<String> emails = (List<String>) query.execute(email);
			return !emails.isEmpty();
		} finally {
			pm.close();
		}
	}

	public void processAndPersist(Func doForEachPlayer) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Query query = pm.newQuery(Player.class);
			query.setOrdering("score desc, changed desc");
			List<Player> players = executeAndCastToList(query);
			for (Player player : players) {
				doForEachPlayer.exec(player);
			}
		} finally {
			pm.close();
		}
	}

	@SuppressWarnings("unchecked")
	private List<Player> executeAndCastToList(Query query) {
		return (List<Player>) query.execute();
	}

	public Player getPlayer(EmailAddressDto email) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			return pm.getObjectById(Player.class, email.address);
		} finally {
			pm.close();
		}
	}

	public void changeScore(EmailAddressDto email, int newScore, EmailAddressDto changedBy) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			Player player = pm.getObjectById(Player.class, email.address);
			player.changeScore(newScore, changedBy);
		} finally {
			pm.close();
		}
	}

	public void create(Player player) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			pm.makePersistent(player);
		} finally {
			pm.close();
		}
	}
}
