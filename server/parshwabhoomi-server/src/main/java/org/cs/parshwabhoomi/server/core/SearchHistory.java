/**
 * parshwabhoomi-server	22-Dec-2017:9:45:40 PM
 */
package org.cs.parshwabhoomi.server.core;

import org.cs.parshwabhoomi.server.dto.search.SearchResultCommonDTO;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class SearchHistory {
	/**
	 * The default period in days taken into consideration while
	 * applying the historic context while serving the search results
	 * for user's search query.
	 */
	public static final int DEFAULT_ACTIONABLE_HISTORY_INTERVAL = 7;
	
	public void save(SearchResultCommonDTO dto){
		
	}
}
