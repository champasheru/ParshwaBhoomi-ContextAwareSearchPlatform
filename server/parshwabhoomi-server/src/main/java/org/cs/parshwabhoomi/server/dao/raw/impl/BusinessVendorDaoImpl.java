/**
 * parshwabhoomi-server	29-Oct-2017:7:21:10 PM
 * saurabh
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.BusinessCategory;
import org.cs.parshwabhoomi.server.model.BusinessVendor;
import org.cs.parshwabhoomi.server.model.ContactInfo;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class BusinessVendorDaoImpl extends AbstractRawDao implements BusinessVendorDao {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao#add(org.cs.parshwabhoomi.server.model.BusinessVendor)
	 */
	@Override
	public int add(BusinessVendor businessVendor) {
		LogManager.getLogger().info("Adding business vendor: "+businessVendor.getName());
    	
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
            	LogManager.getLogger().info("The BusinessVendor added successfully!");
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("__Couldn't add vendor info", sqle);
        } finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("__Couldn't add vendor info", e);
			}
		}
        return status;
	}

	@Override
	public BusinessVendor getByUsername(String username) {
		LogManager.getLogger().info("Retrieving vendor profile...");
		
		String query = "SELECT business_vendors.id as vid, category_id, name, route_or_lane, sublocality, locality, state, advertisement_and_offerings, "
				+"pincode, latitude, longitude, primary_mobile, secondary_mobile, landline, email, tagline, user_id, "
				+"categories.id as cid, category_name, category_description "
				+ "FROM ((business_vendors INNER JOIN user_creds ON user_creds.id = business_vendors.user_id) "
				+"INNER JOIN categories ON categories.id = business_vendors.category_id) "
				+ "WHERE username = ?";

		LogManager.getLogger().info("Query:\n" + query);

		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		BusinessVendor vendor = null;
		try {
			prepStmt = connection.prepareStatement(query);
			prepStmt.setString(1, username);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				vendor = new BusinessVendor();
				vendor.setId(rs.getLong("vid"));
				vendor.setName(rs.getString("name"));
				
				Address address = new Address();
				address.setLatitude(rs.getString("latitude"));
				address.setLongitude(rs.getString("longitude"));
				address.setRouteOrLane(rs.getString("route_or_lane"));
				address.setSublocality(rs.getString("sublocality"));
				address.setLocality(rs.getString("locality"));
				address.setState(rs.getString("state"));
				address.setPincode(rs.getString("pincode"));
				vendor.setAddress(address);

				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setEmail(rs.getString("email"));
				contactInfo.setLandline(rs.getString("landline"));
				contactInfo.setPrimaryMobile(rs.getString("primary_mobile"));
				contactInfo.setSecondaryMobile(rs.getString("secondary_mobile"));
				vendor.setContactInfo(contactInfo);

				vendor.setOfferings(rs.getString("advertisement_and_offerings"));
				vendor.setTagLine(rs.getString("tagline"));
				
				String catrgoryName = rs.getString("category_name");
				if(catrgoryName != null){
					BusinessCategory bc = BusinessCategory.valueOf(rs.getString("category_name"));
					vendor.setBusinessCategory(bc);
				}
			}
		} catch (SQLException sqle) {
			LogManager.getLogger().error("Error:retrieving vendor profile",  sqle);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if(connection != null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return vendor;
	}

}
