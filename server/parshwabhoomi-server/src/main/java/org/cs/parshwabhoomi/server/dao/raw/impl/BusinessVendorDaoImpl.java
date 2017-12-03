/**
 * parshwabhoomi-server	29-Oct-2017:7:21:10 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao;
import org.cs.parshwabhoomi.server.model.BusinessVendor;

/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
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

}
