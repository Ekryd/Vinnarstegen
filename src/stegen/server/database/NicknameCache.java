package stegen.server.database;

import net.sf.jsr107cache.*;

public class NicknameCache {

	private final Cache cache;

	public NicknameCache(Cache cache) {
		this.cache = cache;
	}

	/**
	 * 
	 * @see net.sf.jsr107cache.Cache#clear()
	 */
	public void clear() {
		cache.clear();
	}

	/**
	 * @param arg0
	 * @return
	 * @see net.sf.jsr107cache.Cache#containsKey(java.lang.Object)
	 */
	public boolean containsKey(String emailAddress) {
		return cache.containsKey(emailAddress);
	}

	/**
	 * @param arg0
	 * @return
	 * @see net.sf.jsr107cache.Cache#get(java.lang.Object)
	 */
	public String get(String emailAddress) {
		return (String) cache.get(emailAddress);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @return
	 * @see net.sf.jsr107cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	public String put(String emailAddress, String nickname) {
		return (String) cache.put(emailAddress, nickname);
	}

	/**
	 * @param arg0
	 * @return
	 * @see net.sf.jsr107cache.Cache#remove(java.lang.Object)
	 */
	public String remove(String emailAddress) {
		return (String) cache.remove(emailAddress);
	}

}
