/**
 * parshwabhoomi-server	29-Oct-2017:7:19:40 PM
 * saurabh
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.UserCredentialDao;
import org.cs.parshwabhoomi.server.model.UserCredential;


/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class UserCredentialDaoImpl extends AbstractRawDao implements UserCredentialDao {
	
	public long addUserCredential(UserCredential userCredential){
        String query = "INSERT INTO user_creds (username,password,role) " + "VALUES (?,?,?)";
        String userIDQuery="SELECT id from user_creds WHERE username='"+userCredential.getUsername()+"'";
        
        PreparedStatement statement = null;
        
        long userID = -1;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, userCredential.getUsername());
            statement.setString(2, userCredential.getPassword());
            statement.setString(3, userCredential.getRole().name());
            
            int status = statement.executeUpdate();
            if (status > 0) {
                LogManager.getLogger().info("User Credential added successfully!");
                rs = connection.createStatement().executeQuery(userIDQuery);
                if(rs.next()){
                    userID = rs.getInt("id");
                }
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("Error:Adding user info!", sqle);
        } finally {
        	try {
        		if(statement != null){
        			statement.close();
        		}
			} catch (SQLException e) {
				LogManager.getLogger().error("Error:Adding user info!", e);
			}
        }
        
        return userID;
    }
	
	//Checks whether the user with given username & password exists.
    public boolean isValidUser(String username,String password){
    	LogManager.getLogger().info("Validating user: "+username);
        String query = "SELECT count(*) "
                       +"FROM user_creds "
                       +"WHERE username = ? "
                       +"and password = ?";
        
        boolean status=false;
        PreparedStatement statement = null;
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
            LogManager.getLogger().error("Error:validating user", sqle);
        } finally {
        	try {
        		if(statement != null){
        			statement.close();
        		}
			} catch (SQLException e) {
				LogManager.getLogger().error("Error:retrieving user creds!", e);
			}
        }
        return status;
    }
}
