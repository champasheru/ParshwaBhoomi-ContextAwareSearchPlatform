/**
 * parshwabhoomi-server	12-Nov-2017:3:59:54 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.util.List;

import org.cs.parshwabhoomi.server.domainobjects.SearchResult;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public interface SearchAggregator {
	public List<SearchResult> getResults(String userAuth, String query);
}
