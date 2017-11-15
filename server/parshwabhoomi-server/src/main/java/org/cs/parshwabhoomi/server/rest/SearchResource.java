/**
 * parshwabhoomi-server	28-Oct-2017:7:02:15 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 *
 */
public interface SearchResource {
public static final String SEARCH_REQUEST_URI = "/search";
	
	@Path(SEARCH_REQUEST_URI)
	@GET
	public Response search(@QueryParam("q") String queryString);
}
