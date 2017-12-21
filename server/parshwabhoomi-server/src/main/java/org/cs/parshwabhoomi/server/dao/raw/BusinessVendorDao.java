/**
 * parshwabhoomi-server	29-Oct-2017:7:20:35 PM
 * saurabh
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw;

import org.cs.parshwabhoomi.server.model.BusinessVendor;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public interface BusinessVendorDao {
	public int add(BusinessVendor businessVendor);
	
	public int update(BusinessVendor businessVendor);
	
	public BusinessVendor getByUsername(String username);
	
	public BusinessVendor getById(long id);
}
