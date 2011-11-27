package stegen.server.memcache;

import java.util.*;

import net.sf.jsr107cache.*;

/**
 * @author Björn Ekryd
 */
public class NicknameCache {

	private final Cache cache;

	public NicknameCache() {
		this.cache = createCache();
	}

	private Cache createCache() {
		try {
			CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
			return cacheFactory.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot create Cache", e);
		}
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

	public void remove(String emailAddress) {
		cache.remove(emailAddress);
	}

}
