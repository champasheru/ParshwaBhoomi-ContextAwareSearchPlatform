package org.cs.parshwabhoomi.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;

import org.cs.parshwabhoomi.server.domainobjects.Address;
import org.cs.parshwabhoomi.server.domainobjects.BusinessCategory;
import org.cs.parshwabhoomi.server.domainobjects.BusinessVendor;
import org.cs.parshwabhoomi.server.domainobjects.ContactInfo;
import org.cs.parshwabhoomi.server.domainobjects.EndUser;
import org.cs.parshwabhoomi.server.domainobjects.SearchResult;
import org.cs.parshwabhoomi.server.domainobjects.SearchResult.Provider;
import org.cs.parshwabhoomi.server.domainobjects.UserCredential;
import org.cs.parshwabhoomi.server.domainobjects.UserCredential.Role;

//Implemented as a Singleton
public class DBManager {
    private Connection connection = null;
    private static DBManager dbManager;

    private DBManager(){
        System.out.println("__Initializing the Database Manager...");
        connect("smartsearch", "pbadmin", "pbadmin123");
    }

    public static DBManager getDBManager(){
        if(dbManager==null){
            dbManager=new DBManager();
        }
        return dbManager;
    }
    
    @SuppressWarnings("rawtypes")
	private void connect(String dbname, String username, String password) {
        System.out.println("Loading MySQL JDBC driver...");
        try {
            Class jdbcDiverClass = Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver ="+jdbcDiverClass);
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname, username, password);
            
            System.out.println("__Connection url ="+connection);

            assert (connection != null) : "__The smartsearch database not found!!\n Unable to load the DB driver.";

            if (connection != null) {
                System.out.println("__Success... Connected to smartsearch DB:" + dbname + "\nConnection=" + connection);
            }
        } catch (Exception ex) {
            System.out.println("__Error:Connecting to  smartsearch DB: " + ex);
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException sqle) {
                System.out.println(sqle);
            } finally {
                connection = null;
            }
        }
    }

    
    private void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqle) {
            connection = null;
            System.out.println(sqle);
        }
    }
    
    
    public ArrayList<SearchResult> getSearchResultsFor(String searchKeyword,String username,String routeLane,String sublocality,String locality) {
        String optionalClause="";
        
        if(routeLane!=null && sublocality!=null){
            optionalClause=optionalClause+"and (route_or_lane like '%"+routeLane+"%' or sublocality like '%"+sublocality+"%')";
        }else if(routeLane!=null){
            optionalClause=optionalClause+"and route_or_lane like '%"+routeLane+"%'";
        }else if(sublocality!=null){
            optionalClause=optionalClause+"and sublocality like '%"+sublocality+"%'";
        }
        
        String query = "SELECT name,category_name,route_or_lane,sublocality,locality,primary_mobile,advertisement_and_offerings "
                       +"FROM user_preferences,vendors,categories,users " 
                       +"WHERE preference like '%"+searchKeyword+"%' "
                       +"and username='"+username+"' "
                       +"and user_preferences.user_id=users.id "
                       +"and user_preferences.category_id=vendors.category_id "
                       +"and categories.id=vendors.category_id "
                       +"and locality='"+locality+"'";
                       
        if(!optionalClause.equals("")){
            query+= " "
                    +optionalClause
                    + " UNION "
                    + "SELECT name,category_name,route_or_lane,sublocality,locality,primary_mobile,advertisement_and_offerings "
                    + "FROM user_preferences,vendors,categories,users "
                    + "WHERE preference like '%" + searchKeyword + "%' "
                    + "and username='" + username + "' "
                    + "and user_preferences.user_id=users.id "
                    + "and user_preferences.category_id=vendors.category_id "
                    + "and categories.id=vendors.category_id "
                    + "and locality='" + locality + "'";
        }
                
                       
        System.out.println("Custom Result for Vendors Query:\n"+query+"\n");
        ArrayList<SearchResult> pBSearchResults = null;
        
        Statement statement=null;
        ResultSet rs = null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=statement.executeQuery(query);
            System.out.println("Done...");
            if (rs.first() == false) {
                return null;
            } else {
                System.out.println("__Custom search results found...");
                pBSearchResults=new ArrayList<SearchResult>();
                rs.beforeFirst();
                while (rs.next()) {
                	SearchResult csr= new SearchResult();
                    csr.setTitle(rs.getString("name"));
                    csr.setCategory(rs.getString("category_name"));
                    Address address = new Address();
                    address.setRouteOrLane(rs.getString("route_or_lane"));
                    address.setSublocality(rs.getString("sublocality"));
                    address.setLocality(rs.getString("locality"));
                    csr.setAddress(address);
                    ContactInfo contactInfo = new ContactInfo();
                    contactInfo.setPrimaryMobile(rs.getString("primary_mobile"));
                    csr.setContactInfo(contactInfo);
                    csr.setSnippet(rs.getString("advertisement_and_offerings"));
                    csr.setProvider(Provider.SEARCH_PROVIDER_PARSHWABHOOMI);
                    pBSearchResults.add(csr);
                }
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:retrieving search results"+ sqle);
        } finally {
            rs = null;
        }
        
        System.out.println("Custom search results count = "+pBSearchResults.size());
        return pBSearchResults;
    }
    
    
    public HashMap<String,BusinessCategory> getCategories(){
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
            System.out.println("__Error:retrieving categories"+ sqle);
        } finally {
            rs = null;
        }
        System.out.println("__Number of Categories found: "+categories.size());
        return categories;
    }
    
    
    //Adds the new user record
    public long addUser(UserCredential userCredential){
        String query = "INSERT INTO users (username,password,role) " + "VALUES (?,?,?)";
        String userIDQuery="SELECT id from users WHERE username='"+userCredential.getUsername()+"'";
        
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
                System.out.println("__The UserCredential added successfully!");
                rs = connection.createStatement().executeQuery(userIDQuery);
                if(rs.next()){
                    userID = rs.getInt("id");
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error:Adding user info " + sqle);
        } finally {
        	try {
        		if(rs != null){
        			rs.close();
        		}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        return userID;
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
    
    
    public int addVendor(BusinessVendor businessVendor){
        String query = "INSERT INTO vendors (category_id,name,route_or_lane,sublocality,locality,primary_mobile,advertisement_and_offerings) " +
                " VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);
            
            statement.setLong(1, businessVendor.getBusinessCategory().getId());
            statement.setString(2, businessVendor.getName());
            statement.setString(3, businessVendor.getAddress().getRouteOrLane());
            statement.setString(4, businessVendor.getAddress().getSublocality());
            statement.setString(5, businessVendor.getAddress().getLocality());
            statement.setString(6, businessVendor.getContactInfo().getPrimaryMobile());
            statement.setString(7, businessVendor.getOfferings());

            status = statement.executeUpdate();
            if (status > 0) {
                System.out.println("__The BusinessVendor added successfully!");
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:Adding vendor info" + sqle);
            sqle.printStackTrace(System.out);
        } finally {
            
        }
        return status;
    }

    /**
     *
     * @param searchQuery
     * @return modifiedSerachQuery
     * This method takes the search keyword/query as submitted by the user.It then makes a keyword based search in the user_preferences
     * table against the preference column & returns the user preference as found. This updated search query is returned which is used by
     * the search service like Yahoo.
     */
    public ArrayList<String> getUserSpecificSearchQueryForSearchService(String searchQuery,String username){
        String query = "SELECT preference,category_name "
                       +"FROM users,user_preferences,categories "
                       +"WHERE preference like '%"+searchQuery+"%' "
                       +"and username='"+username+"' "
                       +"and users.id=user_preferences.user_id "
                       +"and user_preferences.category_id=categories.id";
                       
        System.out.println("__Query:\n"+query+"\n");
        ArrayList<String> userSpecificSearchQuery = null;

        Statement statement=null;
        ResultSet rs = null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=statement.executeQuery(query);
            if (!rs.first()) {
                return null;
            } else {
                userSpecificSearchQuery=new ArrayList<String>();
                rs.beforeFirst();
                while(rs.next()) {//If the user preferences belong to more than 1 category
                    userSpecificSearchQuery.add(rs.getString("preference")+" "+rs.getString("category_name"));
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error:retrieving search results"+ sqle);
        } finally {
            rs = null;
        }
        System.out.print("__User specific search query count= "+userSpecificSearchQuery.size());
        return userSpecificSearchQuery;
    }


    //Checks whether the user with given username & password exists.
    public boolean isValidUser(String username,String password){
        String query = "SELECT count(*) "
                       +"FROM users "
                       +"WHERE username='"+username+"' "
                       +"and password='"+password+"'";
                       
        System.out.println("__Query:\n"+query+"\n");
        
        boolean status=false;
        Statement statement=null;
        ResultSet rs = null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs=statement.executeQuery(query);
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


    //TODO: needs DB schema update to include address and contact info fields.
    //Same method may also be desirable but with user ID.
    public EndUser getEndUserDetailedProfile(String username){
        String userBasicInfoQuery = "SELECT id as user_creds_id, role, end_users.*"
                       +"FROM user_creds inner join end_users on user_creds.id = end_users.user_id "
                       +"WHERE username='"+username+"' ";

        String userPrefsInfoQuery = "SELECT category_name,preference "
                       +"FROM user_preferences,categories "
                       +"WHERE categories.id=user_preferences.category_id "
                       +"and user_id=? group by category_name,preference";
        
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
            	
                long userID = rs.getLong("id");
                userCredential=new UserCredential();
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
                contactInfo.setPrimaryMobile(rs.getString("primaryMobile"));
                contactInfo.setSecondaryMobile(rs.getString("secondaryMobile"));
                endUser.setContactInfo(contactInfo);
                
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

    public void updateUserProfile(EndUser endUser){
        int userID = this.updateUserInfo(endUser);
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
    private int updateUserInfo(EndUser endUser){
        String query = "UPDATE end_users SET route_or_lane=?, sublocality=?, locality=?, state=?, pincode=?, latitude=?, longitude=?, "
        				+ "primaryMobile=?, secondaryMobile=?, landline=?, email=?, education=?, work=? "
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
            statement.setString(10, endUser.getContactInfo().getEmail());
            statement.setString(11, endUser.getEducationInfo());
            statement.setString(12, endUser.getWorkInfo());
            statement.setLong(13, endUser.getUserCredential().getId());
            
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
    
    //When this object is being garbage collected,release/close the database connection.
    @Override
    public void finalize(){
        try {
            close();
            super.finalize();
        } catch (Throwable ex) {
            ex.printStackTrace(System.out);
        }
    }
}
