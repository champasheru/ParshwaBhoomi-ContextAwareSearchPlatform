/**
 * parshwabhoomi-server	01-Dec-2017:10:22:26 PM
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.BusinessCategoryDao;
import org.cs.parshwabhoomi.server.model.BusinessCategory;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class BusinessCategoryDaoImpl extends AbstractRawDao implements BusinessCategoryDao {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.BusinessCategoryDao#getCategories()
	 */
	@Override
	public HashMap<String, BusinessCategory> getCategories() {
		LogManager.getLogger().info("Retrieving business categories...");
        String query="SELECT id,category_name,category_description FROM categories";
        
        Statement statement=null;
        ResultSet rs = null;
        HashMap<String,BusinessCategory> categories=null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=statement.executeQuery(query);
                        
            if (rs.first() == false) {
                return null;
            } else {
                categories=new HashMap<String, BusinessCategory>();
                rs.beforeFirst();
                while (rs.next()) {
                	BusinessCategory category = BusinessCategory.valueOf(rs.getString("category_name"));
                    categories.put(category.name(), category);
                }
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("__Error:retrieving categories", sqle);
        } finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("__Error:retrieving categories", e);
			}
        }
        
        LogManager.getLogger().info("Number of Categories found: "+categories.size());
        
        return categories;
	}

}
