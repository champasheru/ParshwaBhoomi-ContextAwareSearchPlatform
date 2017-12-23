/**
 * parshwabhoomi-server	29-Oct-2017:7:23:06 PM
 * saurabh
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.SearchDao;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.BusinessCategory;
import org.cs.parshwabhoomi.server.model.ContactInfo;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.model.SearchResult.Provider;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class SearchDaoImpl extends AbstractRawDao implements SearchDao {
	
	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.SearchDao#findByMatchingUserPref(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SearchResult> findByMatchingUserPref(String searchKeyword, String username) {
		String query = "SELECT business_vendors.user_id as bvid, name, tagline, offerings, category_name, "
				+ "route_or_lane, sublocality, locality, state, pincode, latitude, longitude, "
				+ "primary_mobile, secondary_mobile, landline, email "
                +"FROM user_preferences, business_vendors, categories, user_creds " 
                +"WHERE lower(preference) like lower(?) "
                +"and username = ? "
                +"and user_preferences.user_id = user_creds.id "
                +"and user_preferences.category_id = business_vendors.category_id "
                +"and categories.id = business_vendors.category_id";
		
		List<SearchResult> results = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, "%"+searchKeyword.trim()+"%");
			statement.setString(2, username.trim());
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				SearchResult sr = new SearchResult();
				sr.setVendorID(String.valueOf(resultSet.getLong("bvid")));
                sr.setTitle(resultSet.getString("name"));
                sr.setTagline(resultSet.getString("tagline"));
                sr.setSnippet(resultSet.getString("offerings"));
                sr.setCategory(BusinessCategory.valueOf(resultSet.getString("category_name")));
                
                Address address = new Address();
                address.setRouteOrLane(resultSet.getString("route_or_lane"));
                address.setSublocality(resultSet.getString("sublocality"));
                address.setLocality(resultSet.getString("locality"));
                address.setState(resultSet.getString("state"));
                address.setPincode(resultSet.getString("pincode"));
                address.setLatitude(resultSet.getString("latitude"));
                address.setLongitude(resultSet.getString("longitude"));
                
                sr.setAddress(address);
                
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setPrimaryMobile(resultSet.getString("primary_mobile"));
                contactInfo.setSecondaryMobile(resultSet.getString("secondary_mobile"));
                contactInfo.setLandline(resultSet.getString("landline"));
                contactInfo.setEmail(resultSet.getString("email"));
                sr.setContactInfo(contactInfo);
                
                sr.setProvider(Provider.SEARCH_PROVIDER_PARSHWABHOOMI);
                
                results.add(sr);
			}
		} catch (SQLException e) {
			LogManager.getLogger().error("Error:retrieving search results"+ e);
		}finally{
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					LogManager.getLogger().error("Error:retrieving search results"+ e);
				}
			}
		}
		
		return results;
	}


	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.SearchDao#findByMatchingVendorOfferings(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SearchResult> findByMatchingVendorOfferings(String searchKeyword, String username) {
		String query = "SELECT business_vendors.user_id as bvid, name, tagline, offerings, category_name, "
				+ "route_or_lane, sublocality, locality, state, pincode, latitude, longitude, "
				+ "primary_mobile, secondary_mobile, landline, email "
                +"FROM business_vendors, categories, user_creds " 
                +"WHERE lower(offerings) like lower(?) "
                +"and username = ? "
                +"and categories.id = business_vendors.category_id";
		
		List<SearchResult> results = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, "%"+searchKeyword.trim()+"%");
			statement.setString(2, username.trim());
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				SearchResult sr= new SearchResult();
				sr.setVendorID(String.valueOf(resultSet.getLong("bvid")));
                sr.setTitle(resultSet.getString("name"));
                sr.setTagline(resultSet.getString("tagline"));
                sr.setSnippet(resultSet.getString("offerings"));
                sr.setCategory(BusinessCategory.valueOf(resultSet.getString("category_name")));
                
                Address address = new Address();
                address.setRouteOrLane(resultSet.getString("route_or_lane"));
                address.setSublocality(resultSet.getString("sublocality"));
                address.setLocality(resultSet.getString("locality"));
                address.setState(resultSet.getString("state"));
                address.setPincode(resultSet.getString("pincode"));
                address.setLatitude(resultSet.getString("latitude"));
                address.setLongitude(resultSet.getString("longitude"));
                sr.setAddress(address);
                
                ContactInfo contactInfo = new ContactInfo();
                contactInfo.setPrimaryMobile(resultSet.getString("primary_mobile"));
                contactInfo.setSecondaryMobile(resultSet.getString("secondary_mobile"));
                contactInfo.setLandline(resultSet.getString("landline"));
                contactInfo.setEmail(resultSet.getString("email"));
                sr.setContactInfo(contactInfo);
                
                sr.setProvider(Provider.SEARCH_PROVIDER_PARSHWABHOOMI);
                
                results.add(sr);
			}
		} catch (SQLException e) {
			LogManager.getLogger().error("Error:retrieving search results"+ e);
		}finally{
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					LogManager.getLogger().error("Error:retrieving search results"+ e);
				}
			}
		}
		
		return results;
	}


    
    /**
     *
     * @param searchQuery
     * @return modifiedSerachQuery
     * This method takes the search keyword/query as submitted by the user.It then makes a keyword based search in the user_preferences
     * table against the preference column & returns the user preference as found. This updated search query is returned which is used by
     * the search service like Yahoo.
     */
    public List<String> getModifiedSearchTermByMatchingUserPref(String searchQuery,String username){
        String query = "SELECT preference, category_name "
                       +"FROM user_creds, user_preferences, categories "
                       +"WHERE lower(preference) like lower(?) "
                       +"and username= ? "
                       +"and user_creds.id = user_preferences.user_id "
                       +"and user_preferences.category_id = categories.id";
        
        List<String> modifiedSearchTerms = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%"+searchQuery+"%");
            statement.setString(2, username);
            rs = statement.executeQuery();
            while(rs.next()) {
        		//If the user preferences belong to more than 1 category
                modifiedSearchTerms.add(rs.getString("preference")+"::"+rs.getString("category_name"));
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("Error:retrieving modified search terms by matching user prefs!", sqle);
        } finally {
            if(statement != null){
            	try {
					statement.close();
				} catch (SQLException e) {
					LogManager.getLogger().error("Error:retrieving modified search terms by matching user prefs!", e);
				}
            }
        }
        
        LogManager.getLogger().info("Modified search terms found by matching user prefs= "+modifiedSearchTerms);
        return modifiedSearchTerms;
    }


	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.SearchDao#getModifiedSearchTermByMatchingVendorOfferings(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getModifiedSearchTermByMatchingUserPrefCategory(String searchQuery, String username) {
		String query = "SELECT category_name "
                +"FROM user_creds, user_preferences, categories "
                +"WHERE lower(preference) like lower(?) "
                +"and username= ? "
                +"and user_creds.id = user_preferences.user_id "
                +"and user_preferences.category_id = categories.id";
        
        List<String> modifiedSearchTerms = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%"+searchQuery+"%");
            statement.setString(2, username);
            rs = statement.executeQuery();
            while(rs.next()) {
        		//If the user preferences belong to more than 1 category
                modifiedSearchTerms.add(rs.getString("category_name"));
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("Error:retrieving modified search terms by matching user prefs category!", sqle);
        } finally {
            if(statement != null){
            	try {
					statement.close();
				} catch (SQLException e) {
					LogManager.getLogger().error("Error:retrieving modified search terms by matching user prefs category!", e);
				}
            }
        }
        
        LogManager.getLogger().info("Modified search terms found by matching user prefs category= "+modifiedSearchTerms);
        return modifiedSearchTerms;
	}


	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.SearchDao#getModifiedSearchTermByMatchingVendorOfferingCategory(java.lang.String, java.lang.String)
	 */
	@Override
	public List<String> getModifiedSearchTermByMatchingVendorOfferingsCategory(String searchQuery, String username) {
		String query = "SELECT category_name "
                +"FROM user_creds, business_vendors, categories "
                +"WHERE lower(offerings) like lower(?) "
                +"and username= ? "
                +"and business_vendors.category_id = categories.id";
        
        List<String> modifiedSearchTerms = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, "%"+searchQuery+"%");
            statement.setString(2, username);
            rs = statement.executeQuery();
            while(rs.next()) {
        		//If the user preferences belong to more than 1 category
                modifiedSearchTerms.add(rs.getString("category_name"));
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("Error:retrieving modified search terms by matching vendor offerings category!", sqle);
        } finally {
            if(statement != null){
            	try {
					statement.close();
				} catch (SQLException e) {
					LogManager.getLogger().error("Error:retrieving modified search terms by matching vendor offerings category!", e);
				}
            }
        }
        
        LogManager.getLogger().info("Modified search terms found by matching vendor offerings category= "+modifiedSearchTerms);
        return modifiedSearchTerms;
	}
}
