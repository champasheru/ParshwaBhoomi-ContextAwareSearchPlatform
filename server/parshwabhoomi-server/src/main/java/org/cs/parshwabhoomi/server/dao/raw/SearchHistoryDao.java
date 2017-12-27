package org.cs.parshwabhoomi.server.dao.raw;

public interface SearchHistoryDao {
	public void save(long userId, String searchResultDtoJson);
}
