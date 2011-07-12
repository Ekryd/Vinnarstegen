package stegen.server.memcache;

import java.util.*;

import net.sf.jsr107cache.*;

public class NicknameCache {

	private final Cache cache;

	public NicknameCache() throws CacheException {
		this.cache = createCache();
	}

	private Cache createCache() throws CacheException {
		CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
		return cacheFactory.createCache(Collections.emptyMap());
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
