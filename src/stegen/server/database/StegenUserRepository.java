package stegen.server.database;

import java.util.*;

import javax.jdo.*;

import net.sf.jsr107cache.*;
import stegen.client.dto.*;

public class StegenUserRepository {
	private static StegenUserRepository instance = new StegenUserRepository();
	private NicknameCache cache;

	private StegenUserRepository() {
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			cache = new NicknameCache(cacheFactory.createCache(Collections.emptyMap()));
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	public static StegenUserRepository get() {
		return instance;
	}

	public String getOrCreateNickname(EmailAddressDto email) {
		if (email.address.isEmpty()) {
			return "";
		}
		if (!hasNickname(email)) {
			createDefaultNickname(email);
		}
		return getNickname(email);
	}

	public PlayerDto createPlayerDto(String emailString) {
		EmailAddressDto email = new EmailAddressDto(emailString);
		String nickname = getOrCreateNickname(email);
		return new PlayerDto(email, nickname);
	}

	boolean hasNickname(EmailAddressDto email) {
		return isUserInCache(email.address) || isUserInDatabase(email.address);
	}

	void createDefaultNickname(EmailAddressDto email) {
		StegenUser user = StegenUser.createUserWithDefaults(email);
		putUserInCache(user);
		putUserInDatabase(user);
	}

	public void updateNickname(EmailAddressDto email, String nickname) {
		String emailString = email.address;

		String currentNickname = getNickname(email);

		boolean sameNickname = currentNickname.equals(nickname);

		if (!sameNickname) {
			StegenUser newStegenUser = StegenUser.createUser(emailString, nickname);
			updateStegenUserInDatabase(newStegenUser);
			putUserInCache(newStegenUser);
		}
	}

	private void updateStegenUserInDatabase(StegenUser stegenUser) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			StegenUser stegenUserFromDatabase = pm.getObjectById(StegenUser.class, stegenUser.getEmailString());
			stegenUserFromDatabase.updateNickname(stegenUser.getNickname());
		} finally {
			pm.close();
		}
	}

	String getNickname(EmailAddressDto email) {
		if (isUserInCache(email.address)) {
			return getUserInCache(email.address);
		}
		if (isUserInDatabase(email.address)) {
			StegenUser user = getUserInDatabase(email.address);
			putUserInCache(user);
			return user.getNickname();
		}
		throw new IllegalStateException("Nickname was not found for " + email.address);
	}

	private boolean isUserInCache(String emailString) {
		return cache.containsKey(emailString);
	}

	private boolean isUserInDatabase(String emailString) {
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

	private void putUserInCache(StegenUser userInDatabase) {
		cache.put(userInDatabase.getEmailString(), userInDatabase.getNickname());
	}

	private void putUserInDatabase(StegenUser user) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			pm.makePersistent(user);
		} finally {
			pm.close();
		}
	}

	private String getUserInCache(String emailString) {
		return cache.get(emailString);
	}

	private StegenUser getUserInDatabase(String emailString) {
		PersistenceManager pm = Pmf.getPersistenceManager();
		try {
			return pm.getObjectById(StegenUser.class, emailString);
		} finally {
			pm.close();
		}
	}

	void clearCache() {
		cache.clear();
	}

}
