/**
 * parshwabhoomi-server	28-Oct-2017:7:02:15 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cs.parshwabhoomi.server.dto.search.SearchRequestDTO;

/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 *
 */
public interface SearchResource {
	public static final String SEARCH_REQUEST_URI = "/q";
	
	@Path(SEARCH_REQUEST_URI)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(SearchRequestDTO requestDTO);
}
