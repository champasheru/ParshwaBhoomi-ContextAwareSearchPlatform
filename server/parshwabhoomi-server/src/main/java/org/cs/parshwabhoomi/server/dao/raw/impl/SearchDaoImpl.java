/**
 * parshwabhoomi-server	29-Oct-2017:7:23:06 PM
 * saurabh
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.SearchDao;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.ContactInfo;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.model.SearchResult.Provider;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class SearchDaoImpl extends AbstractRawDao implements SearchDao {
	
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
                       +"FROM user_preferences,business_vendors,categories,user_creds " 
                       +"WHERE preference like '%"+searchKeyword+"%' "
                       +"and username='"+username+"' "
                       +"and user_preferences.user_id=user_creds.id "
                       +"and user_preferences.category_id=business_vendors.category_id "
                       +"and categories.id=business_vendors.category_id "
                       +"and locality='"+locality+"'";
                       
        if(!optionalClause.equals("")){
            query+= " "
                    +optionalClause
                    + " UNION "
                    + "SELECT name,category_name,route_or_lane,sublocality,locality,primary_mobile,advertisement_and_offerings "
                    + "FROM user_preferences,business_vendors,categories,user_creds "
                    + "WHERE preference like '%" + searchKeyword + "%' "
                    + "and username='" + username + "' "
                    + "and user_preferences.user_id=user_creds.id "
                    + "and user_preferences.category_id=business_vendors.category_id "
                    + "and categories.id=business_vendors.category_id "
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
        	LogManager.getLogger().error("Error:retrieving search results"+ sqle);
        } finally {
            rs = null;
        }
        
        LogManager.getLogger().info("Custom search results count = "+pBSearchResults.size());
        return pBSearchResults;
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
                       +"FROM user_creds,user_preferences,categories "
                       +"WHERE preference like '%"+searchQuery+"%' "
                       +"and username='"+username+"' "
                       +"and user_creds.id=user_preferences.user_id "
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
        	LogManager.getLogger().error("Error:retrieving search results"+ sqle);
        } finally {
            rs = null;
        }
        
        LogManager.getLogger().info("User pref specific search query count= "+userSpecificSearchQuery.size());
        return userSpecificSearchQuery;
    }
}
