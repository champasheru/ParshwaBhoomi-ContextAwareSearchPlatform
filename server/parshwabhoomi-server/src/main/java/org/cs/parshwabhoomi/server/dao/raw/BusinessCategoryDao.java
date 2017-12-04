/**
 * parshwabhoomi-server	01-Dec-2017:10:20:33 PM
 */
package org.cs.parshwabhoomi.server.dao.raw;

import java.util.HashMap;

import org.cs.parshwabhoomi.server.model.BusinessCategory;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public interface BusinessCategoryDao {
	public HashMap<String,BusinessCategory> getCategories();
}
