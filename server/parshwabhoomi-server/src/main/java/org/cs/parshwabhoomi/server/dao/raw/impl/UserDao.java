/**
 * parshwabhoomi-server	29-Oct-2017:7:19:40 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.IUserDao;


/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 *
 */
public class UserDao extends AbstractRawDao implements IUserDao {
	
	//Checks whether the user with given username & password exists.
    public boolean isValidUser(String username,String password){
    	LogManager.getLogger().info("Validating user: "+username);
        String query = "SELECT count(*) "
                       +"FROM users "
                       +"WHERE username = ? "
                       +"and password = ?";
        
        boolean status=false;
        PreparedStatement statement=null;
        ResultSet rs = null;
        try {
        	statement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        	statement.setString(1, username);
        	statement.setString(2, password);
        	rs = statement.executeQuery();
            if (rs.next()) {
                int count=rs.getInt(1);
                if(count==1){
                    status=true;
                }
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:retrieving search results"+ sqle);
        } finally {
            rs = null;
        }
        return status;
    }
}
