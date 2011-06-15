package stegen.server.database;

import javax.jdo.*;

public final class Pmf {
	private static final PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	private Pmf() {}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}
}
