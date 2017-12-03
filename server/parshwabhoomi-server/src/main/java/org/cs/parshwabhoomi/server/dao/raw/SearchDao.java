/**
 * parshwabhoomi-server	29-Oct-2017:7:22:28 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw;

import java.util.ArrayList;

import org.cs.parshwabhoomi.server.model.SearchResult;

/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 *
 */
public interface SearchDao {
	public ArrayList<SearchResult> getSearchResultsFor(String searchKeyword,String username,String routeLane,String sublocality,String locality);
	
	public ArrayList<String> getUserSpecificSearchQueryForSearchService(String searchQuery,String username);
}
