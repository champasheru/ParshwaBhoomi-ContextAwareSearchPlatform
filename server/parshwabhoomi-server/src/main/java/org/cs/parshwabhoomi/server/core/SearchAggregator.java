/**
 * parshwabhoomi-server	12-Nov-2017:3:59:54 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.util.List;

import org.cs.parshwabhoomi.server.model.SearchContext;
import org.cs.parshwabhoomi.server.model.SearchResult;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public interface SearchAggregator {
	public List<SearchResult> getResults(SearchContext context);
}
