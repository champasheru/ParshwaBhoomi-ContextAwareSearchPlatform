package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.SearchHistoryDao;

public class SearchHistoryDaoImpl extends AbstractRawDao implements SearchHistoryDao {

	@Override
	public void save(long userId, String searchResultDtoJson) {
		LogManager.getLogger().info("Adding record to search history for user:"+userId);
		
		String query = "INSERT INTO "
				+ "search_history(user_id, result)"
				+ " values(?, ?)";
		
		PreparedStatement statement = null;
		try{
			statement = connection.prepareStatement(query);
			statement.setLong(1, userId);
			statement.setString(2, searchResultDtoJson);
			
			int status = statement.executeUpdate();
			if(status > 0){
				LogManager.getLogger().info("Search history saved");
			}else{
				LogManager.getLogger().error("Couldn't save search history!");
			}
		}catch(SQLException e){
			LogManager.getLogger().error("Couldn't save search history!");
		}finally{
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					LogManager.getLogger().error("Couldn't save search history!");
				}
			}
		}
	}
}
