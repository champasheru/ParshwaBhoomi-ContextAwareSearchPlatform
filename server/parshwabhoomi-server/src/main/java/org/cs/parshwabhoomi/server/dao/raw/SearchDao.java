/**
 * parshwabhoomi-server	29-Oct-2017:7:22:28 PM
 * gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw;

import java.util.List;

import org.cs.parshwabhoomi.server.model.SearchResult;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public interface SearchDao {
	public List<SearchResult> findByMatchingUserPref(String searchKeyword, String username);
	
	public List<SearchResult> findByMatchingVendorOfferings(String searchKeyword, String username);
	
	public List<String> getModifiedSearchTermByMatchingUserPref(String searchQuery, String username);
	
	public List<String> getModifiedSearchTermByMatchingUserPrefCategory(String searchQuery, String username);
	
	public List<String> getModifiedSearchTermByMatchingVendorOfferingsCategory(String searchQuery, String username);
}
