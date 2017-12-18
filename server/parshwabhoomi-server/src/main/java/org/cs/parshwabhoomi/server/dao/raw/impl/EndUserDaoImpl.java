/**
 * parshwabhoomi-server	01-Dec-2017:10:14:15 PM
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.EnumMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.EndUserDao;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.BusinessCategory;
import org.cs.parshwabhoomi.server.model.ContactInfo;
import org.cs.parshwabhoomi.server.model.EndUser;
import org.cs.parshwabhoomi.server.model.UserCredential;
import org.cs.parshwabhoomi.server.model.UserCredential.Role;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class EndUserDaoImpl extends AbstractRawDao implements EndUserDao {
	public void addUserProfile(EndUser endUser){
    	addBasicUserProfile(endUser);
    	addUserPreferences(endUser);
    }
    
    private void addBasicUserProfile(EndUser endUser){
    	String query = "INSERT INTO end_users(user_id, name, dob, education, work, route_or_lane, sublocality, locality, pincode, state, latitude, longitude, "
    			+ "primary_mobile, secondary_mobile, landline, email) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    	
    	PreparedStatement statement = null;
    	try{
    		statement = connection.prepareStatement(query);
    		
    		statement.setLong(1, endUser.getUserCredential().getId());
    		statement.setString(2, endUser.getName());
    		statement.setDate(3, new Date(endUser.getDateOfBirth().getTime()));
    		statement.setString(4, endUser.getEducationInfo());
    		statement.setString(5, endUser.getWorkInfo());
    		statement.setString(6, endUser.getAddress().getRouteOrLane());
    		statement.setString(7, endUser.getAddress().getSublocality());
    		statement.setString(8, endUser.getAddress().getLocality());
    		statement.setString(9, endUser.getAddress().getPincode());
    		statement.setString(10, endUser.getAddress().getState());
    		statement.setString(11, endUser.getAddress().getLatitude());
    		statement.setString(12, endUser.getAddress().getLongitude());
    		statement.setString(13, endUser.getContactInfo().getPrimaryMobile());
    		statement.setString(14, endUser.getContactInfo().getSecondaryMobile());
    		statement.setString(15, endUser.getContactInfo().getLandline());
    		statement.setString(16, endUser.getContactInfo().getEmail());
    		
    		int status = statement.executeUpdate();
    		if(status > 0){
    			LogManager.getLogger().info("Basic user profile info added successfully!");
    		}
    	}catch(SQLException e){
    		LogManager.getLogger().error("Couldn't add basic user profile!", e);
    	}
    }
    
    private void addUserPreferences(EndUser endUser){
        String query = "INSERT INTO user_preferences (user_id,category_id,preference) " + "VALUES (?,?,?)";

        System.out.println("__Adding the user preferences for user: "+endUser.getUserCredential().getUsername());
        PreparedStatement statement = null;
        
        int status = 0;
        try {
            System.out.println("user_id received= "+endUser.getUserCredential().getId());
            statement = connection.prepareStatement(query);
            statement.setLong(1, endUser.getUserCredential().getId());
            
            for(Iterator<BusinessCategory> iterator = endUser.getUserPrefs().keySet().iterator(); iterator.hasNext(); ){
            	BusinessCategory category = iterator.next();
            	String pref = endUser.getUserPrefs().get(category);
                if(pref!=null && !pref.trim().equals("")){
                	statement.setLong(2, category.getId());
                    statement.setString(3, pref.trim());
                    status = statement.executeUpdate();
                    if (status > 0) {
                    	System.out.println("__The MULTIPLE UserCredential Preference added successfully for category: "+category.name());
                    }
               }//end of if the pref for category has some valid value
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:Adding user preference " + sqle);
        } finally {
            
        }
    }
    
    
    public void updateUserProfile(EndUser endUser){
        int userID = this.updateBasicUserProfile(endUser);
        if(userID > 0){
            String query = "DELETE FROM user_preferences WHERE user_id='"+userID+"'";
            
            Statement statement = null;
            int status = 0;
            try {
                statement = connection.createStatement();
                status=statement.executeUpdate(query);
                if (status > 0) {
                    System.out.println("__All the previous UserCredential Preferences deleted successfully...");
                    this.addUserPreferences(endUser);
                }
            } catch (SQLException sqle) {
                System.out.println("__Error:Updating the USER PREFS info" + sqle);
            } finally {
            	if(statement != null){
            		try {
						statement.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            }
        }
    }

    
    //Updates the existing user record & also returns the user id of the record from the users table of the db.
    private int updateBasicUserProfile(EndUser endUser){
        String query = "UPDATE end_users SET route_or_lane=?, sublocality=?, locality=?, state=?, pincode=?, latitude=?, longitude=?, "
        				+ "primary_mobile=?, secondary_mobile=?, landline=?, email=?, education=?, work=? "
                        +"WHERE user_id=?";

        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);
            
            statement.setString(1, endUser.getAddress().getRouteOrLane());
            statement.setString(2, endUser.getAddress().getSublocality());
            statement.setString(3, endUser.getAddress().getLocality());
            statement.setString(4, endUser.getAddress().getState());
            statement.setString(5, endUser.getAddress().getPincode());
            statement.setString(6, endUser.getAddress().getLatitude());
            statement.setString(7, endUser.getAddress().getLongitude());
            statement.setString(8, endUser.getContactInfo().getPrimaryMobile());
            statement.setString(9, endUser.getContactInfo().getSecondaryMobile());
            statement.setString(10, endUser.getContactInfo().getLandline());
            statement.setString(11, endUser.getContactInfo().getEmail());
            statement.setString(12, endUser.getEducationInfo());
            statement.setString(13, endUser.getWorkInfo());
            statement.setLong(14, endUser.getUserCredential().getId());
            
            status = statement.executeUpdate();
            if (status > 0) {
                System.out.println("__The UserCredential Basic Info updated successfully!");
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:Updating the Basic UserCredential info" + sqle);
        } finally {
        	if(statement != null){
        		try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
        
        return status;
    }
    
    
    //TODO: needs DB schema update to include address and contact info fields.
    //Same method may also be desirable but with user ID.
    public EndUser getEndUserDetailedProfile(String username){
        String userBasicInfoQuery = "SELECT user_creds.id as user_creds_id, role, end_users.* "
                       +"FROM user_creds inner join end_users on user_creds.id = end_users.user_id "
                       +"WHERE username='"+username+"' ";

        String userPrefsInfoQuery = "SELECT category_name,preference "
                       +"FROM user_preferences,categories "
                       +"WHERE categories.id=user_preferences.category_id "
                       +"and user_id = ? group by category_name, preference";
        
        System.out.println("__Query:\n"+userBasicInfoQuery+"\n");

        Statement statement=null;
        PreparedStatement userPrefsStmt=null;
        ResultSet rs = null;
        ResultSet resultSet=null;
        UserCredential userCredential=null;
        EndUser endUser = null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            userPrefsStmt=connection.prepareStatement(userPrefsInfoQuery);

            rs=statement.executeQuery(userBasicInfoQuery);
            if (rs.next()) {
            	endUser = new EndUser();
            	
                long userID = rs.getLong("user_creds_id");
                userCredential=new UserCredential();
                userCredential.setUsername(username);
                userCredential.setId(userID);
                userCredential.setRole(Role.valueOf(rs.getString("role")));
                endUser.setUserCredential(userCredential);
                
                Address address = new Address();
                address.setLatitude(rs.getString("latitude"));
                address.setLongitude(rs.getString("longitude"));
                address.setRouteOrLane(rs.getString("route_or_lane"));
                address.setSublocality(rs.getString("sublocality"));
                address.setLocality(rs.getString("locality"));
                address.setState(rs.getString("state"));
                address.setPincode(rs.getString("pincode"));
                endUser.setAddress(address);
                
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setEmail(rs.getString("email"));
                contactInfo.setLandline(rs.getString("landline"));
                contactInfo.setPrimaryMobile(rs.getString("primary_mobile"));
                contactInfo.setSecondaryMobile(rs.getString("secondary_mobile"));
                endUser.setContactInfo(contactInfo);
                
                endUser.setEducationInfo(rs.getString("education"));
                endUser.setWorkInfo(rs.getString("work"));
                
                userPrefsStmt.setLong(1, userID);
                resultSet = userPrefsStmt.executeQuery();
                EnumMap<BusinessCategory,String> userPrefs = new EnumMap<>(BusinessCategory.class);
                while(resultSet.next()){
                    String categoryName = resultSet.getString("category_name");
                    BusinessCategory businessCategory = BusinessCategory.valueOf(categoryName);
                    String preference=resultSet.getString("preference");
                    System.out.println("_Pref for category= "+categoryName+" preference= "+preference);
                    userPrefs.put(businessCategory, preference);
                }
                endUser.setUserPrefs(userPrefs);
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:retrieving user profile"+ sqle);
        } finally {
        	if(rs != null){
        		try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(resultSet != null){
        		try {
        			resultSet.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
            rs = null;
            resultSet=null;
        }
        
        return endUser;
    }
}
