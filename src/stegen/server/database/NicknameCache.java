package stegen.server.database;

import net.sf.jsr107cache.*;

public class NicknameCache {

	private final Cache cache;

	public NicknameCache(Cache cache) {
		this.cache = cache;
	}

	public void clear() {
		cache.clear();
	}

	public boolean containsKey(String emailAddress) {
		return cache.containsKey(emailAddress);
	}

	public String get(String emailAddress) {
		return (String) cache.get(emailAddress);
	}

	public String put(String emailAddress, String nickname) {
		return (String) cache.put(emailAddress, nickname);
	}

	public String remove(String emailAddress) {
		return (String) cache.remove(emailAddress);
	}

}
