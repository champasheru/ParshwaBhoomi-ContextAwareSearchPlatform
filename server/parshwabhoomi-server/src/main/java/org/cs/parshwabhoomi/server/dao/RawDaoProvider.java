package org.cs.parshwabhoomi.server.dao;

public interface RawDaoProvider extends DaoProvider<RawDao> {
	public static final String CONN_URL="conn.url";
	public static final String CONN_USERNAME="conn.username";
	public static final String CONN_PASSWORD="conn.password";
	public static final String CONN_DRIVER="conn.driver";
}
