package org.cs.parshwabhoomi.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.cs.parshwabhoomi.server.datastore.BusinessCategory;
import org.cs.parshwabhoomi.server.datastore.Config;
import org.cs.parshwabhoomi.server.datastore.CustomSearchResult;
import org.cs.parshwabhoomi.server.datastore.User;
import org.cs.parshwabhoomi.server.datastore.Vendor;

//Implemented as a Singleton
public class DBManager {
    private Connection connection = null;
    private static DBManager dbManager;

    private DBManager(){
        System.out.println("__Initializing the Database Manager...");
        //connect("smartsearch", "root", "");
        connect("smartsearch", "pbadmin", "pbadmin123");
    }

    public static DBManager getDBManager(){
        if(dbManager==null){
            dbManager=new DBManager();
        }
        return dbManager;
    }
    
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
    
    
    public ArrayList<CustomSearchResult> getSearchResultsFor(String searchKeyword,String username,String routeLane,String sublocality,String locality) {
        String optionalClause="";
        
        if(routeLane!=null && sublocality!=null){
            optionalClause=optionalClause+"and (route_or_lane like '%"+routeLane+"%' or sublocality like '%"+sublocality+"%')";
        }else if(routeLane!=null){
            optionalClause=optionalClause+"and route_or_lane like '%"+routeLane+"%'";
        }else if(sublocality!=null){
            optionalClause=optionalClause+"and sublocality like '%"+sublocality+"%'";
        }
        
        String query = "SELECT name,category_name,route_or_lane,sublocality,locality,contact,advertisement_and_offerings "
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
                    + "SELECT name,category_name,route_or_lane,sublocality,locality,contact,advertisement_and_offerings "
                    + "FROM user_preferences,vendors,categories,users "
                    + "WHERE preference like '%" + searchKeyword + "%' "
                    + "and username='" + username + "' "
                    + "and user_preferences.user_id=users.id "
                    + "and user_preferences.category_id=vendors.category_id "
                    + "and categories.id=vendors.category_id "
                    + "and locality='" + locality + "'";
        }
                
                       
        System.out.println("Custom Result for Vendors Query:\n"+query+"\n");
        ArrayList<CustomSearchResult> customSearchResults = null;
        
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
                customSearchResults=new ArrayList<CustomSearchResult>();
                String address="";
                rs.beforeFirst();
                while (rs.next()) {
                    CustomSearchResult csr= new CustomSearchResult();
                    csr.setName(rs.getString("name"));
                    csr.setCategory(rs.getString("category_name"));
                    address=address+rs.getString("route_or_lane")+","+rs.getString("sublocality")+","+rs.getString("locality");
                    csr.setAddress(address);
                    csr.setContact(rs.getInt("contact"));
                    csr.setOfferings(rs.getString("advertisement_and_offerings"));
                    customSearchResults.add(csr);
                }
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:retrieving search results"+ sqle);
        } finally {
            rs = null;
        }
        
        System.out.println("Custom search results count = "+customSearchResults.size());
        return customSearchResults;
    }
    
    //Change it to HashMap<String,Integer> whereby returning (category_id,category_name)
    //pairs.
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
                    BusinessCategory category=new BusinessCategory();
                    category.setId(rs.getInt("id"));
                    category.setName(rs.getString("category_name"));
                    category.setUiDescription(rs.getString("category_description"));
                    categories.put(category.getName(), category);
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
    
    public void addUser(User user){
        //First add the basic user info into the users table 
        int userID=this.addUserInfo(user);
        if(userID>0){
            this.addUserPreferences(userID,user.getUserPrefs());
        }
    }
    
    //Adds the new user record
    private int addUserInfo(User user){
        String query = "INSERT INTO users (username,password,address,contact_no,education,work) " + "VALUES (?,?,?,?,?,?)";
        String userIDQuery="SELECT id from users WHERE username='"+user.getName()+"'";
        
        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAddress());
            statement.setInt(4, user.getContactNo());
            statement.setString(5, user.getEducationInfo());
            statement.setString(6, user.getWorkInfo());

            status = statement.executeUpdate();
            if (status > 0) {
                System.out.println("__The User added successfully!");
                ResultSet rs=connection.createStatement().executeQuery(userIDQuery);
                if(rs.next()){
                    return rs.getInt("id");
                }else{
                    return 0;
                }
            }
        } catch (SQLException sqle) {
            System.out.println("Error:Adding user info " + sqle);
        } finally {
            
        }
        return status;
    }

    
    private void addUserPreferences(int userID,HashMap<String,String> userPrefs){
        String query = "INSERT INTO user_preferences (user_id,category_id,preference) " + "VALUES (?,?,?)";

        System.out.println("__Adding the user preferences...");
        PreparedStatement statement = null;
        //Statement lastInsertIDStmt=null;
        int status = 0;
        try {
            //1st get the user_id which is the auto-increment id foreign key from the users table
            //lastInsertIDStmt=connection.createStatement();
            //ResultSet rs=lastInsertIDStmt.executeQuery("SELECT LAST_INSERT_ID()");
            //int userID=-1;
            //if(rs.next()){
            //    userID=rs.getInt(1);
            //    System.out.println("user_id= "+userID);
            //}
            System.out.println("user_id received= "+userID);
            statement = connection.prepareStatement(query);
            statement.setInt(1, userID);
            
            Iterator<BusinessCategory> categories=Config.getCategories().iterator();
            //Add the user preferences for all the categories in the user_preferences table.
            //A typical row in user_preferences table wd be- (id,user_id,category_id,preference)
            while(categories.hasNext()){
                BusinessCategory category=categories.next();
                String pref=userPrefs.get(category.getName());
                if(pref!=null && !pref.trim().equals("")){
                    String[] temp=pref.split(",");

                    //If the num of preferences are greater than  1
                    if(temp.length>0){
                        for(int i=0;i<temp.length;i++){
                            if(!temp[i].trim().equals("")){
                                statement.setInt(2, category.getId());
                                statement.setString(3, temp[i]);
                                status = statement.executeUpdate();
                            }                           
                        }
                        if (status > 0) {
                            System.out.println("__The MULTIPLE User Preference added successfully for category: "+category.getName());
                        }
                    }else{//If only single pref is available,then add it directly.
                        statement.setInt(2, category.getId());
                        statement.setString(3, pref);
                        status = statement.executeUpdate();
                        if (status > 0) {
                            System.out.println("__The SINGLE User Preference added successfully!");
                        }
                    }
               }//end of if the pref for category has some valid value
            }//end of while
        } catch (SQLException sqle) {
            System.out.println("__Error:Adding user preference " + sqle);
        } finally {
            
        }
    }
    
    
    public int addVendor(Vendor vendor){
        String query = "INSERT INTO vendors (category_id,name,route_or_lane,sublocality,locality,contact,advertisement_and_offerings) " +
                " VALUES (?,?,?,?,?,?,?)";
        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);

            statement.setInt(1, Config.getValue(vendor.getBusinessCategory()).getId());
            statement.setString(2, vendor.getVendorName());
            statement.setString(3, vendor.getRootOrLane());
            statement.setString(4, vendor.getSublocality());
            statement.setString(5, vendor.getLocality());
            statement.setInt(6, vendor.getContactNo());
            statement.setString(7, vendor.getOfferings());

            status = statement.executeUpdate();
            if (status > 0) {
                System.out.println("__The Vendor added successfully!");
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


    public User getUserProfile(String username){
        String userBasicInfoQuery = "SELECT id,address,contact_no,education,work "
                       +"FROM users "
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
        User user=null;
        try {
            statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            userPrefsStmt=connection.prepareStatement(userPrefsInfoQuery);

            rs=statement.executeQuery(userBasicInfoQuery);
            if (rs.next()) {
                int userID=rs.getInt("id");
                userPrefsStmt.setInt(1, userID);
                user=new User();
                user.setAddress(rs.getString("address"));
                user.setContactNo(rs.getInt("contact_no"));
                user.setEducationInfo(rs.getString("education"));
                user.setWork(rs.getString("work"));

                resultSet=userPrefsStmt.executeQuery();
                HashMap<String,String> userPrefs=new HashMap<String,String>();
                Iterator<BusinessCategory> categories=Config.getCategories().iterator();
                while(categories.hasNext()){
                    userPrefs.put(categories.next().getName(), "");
                }
                while(resultSet.next()){
                    String categoryName=resultSet.getString("category_name");
                    String preference=resultSet.getString("preference");
                    System.out.println("_Pref for category= "+categoryName+" preference= "+preference);
                    userPrefs.put(categoryName, userPrefs.get(categoryName)+preference+",");
                }
                user.setUserPrefs(userPrefs);
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:retrieving user profile"+ sqle);
        } finally {
            rs = null;
            resultSet=null;
        }
        return user;
    }

    public void updateUserProfile(User user){
        int userID=this.updateUserInfo(user);
        if(userID>0){
            String query = "DELETE FROM user_preferences WHERE user_id='"+userID+"'";
            
            Statement statement = null;
            int status = 0;
            try {
                statement = connection.createStatement();
                status=statement.executeUpdate(query);
                if (status > 0) {
                    System.out.println("__All the previous User Preferences deleted successfully...");
                    this.addUserPreferences(userID,user.getUserPrefs());
                }
            } catch (SQLException sqle) {
                System.out.println("__Error:Updating the USER PREFS info" + sqle);
            } finally {
            }
        }
    }

    //Updates the existing user record & also returns the user id of the record from the users table of the db.
    private int updateUserInfo(User user){
        String query = "UPDATE users SET address=?,contact_no=?,education=?,work=? "
                       +"WHERE username=?";

        String userIDQuery="SELECT id from users WHERE username='"+user.getName()+"'";
        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getAddress());
            statement.setInt(2, user.getContactNo());
            statement.setString(3, user.getEducationInfo());
            statement.setString(4, user.getWorkInfo());
            statement.setString(5, user.getName());
            
            status = statement.executeUpdate();
            if (status > 0) {
                System.out.println("__The User Basic Info updated successfully!");
                ResultSet rs=connection.createStatement().executeQuery(userIDQuery);
                if(rs.next()){
                    return rs.getInt("id");
                }else{
                    return 0;
                }
            }
        } catch (SQLException sqle) {
            System.out.println("__Error:Updating the Basic User info" + sqle);
        } finally {

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
