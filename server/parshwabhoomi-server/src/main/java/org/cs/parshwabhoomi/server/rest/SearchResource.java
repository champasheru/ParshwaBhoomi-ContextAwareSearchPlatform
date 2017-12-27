/**
 * parshwabhoomi-server	28-Oct-2017:7:02:15 PM
 * gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cs.parshwabhoomi.server.dto.search.SearchRequestDTO;
import org.cs.parshwabhoomi.server.dto.search.SearchResultCommonDTO;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public interface SearchResource {
	public static final String SEARCH_REQUEST_URI = "/q";
	public static final String SEARCH_HISTORY_REQUEST_URI = "/history";
	
	@Path(SEARCH_REQUEST_URI)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(SearchRequestDTO requestDTO);
	
	/**
	 * 
	 * @param requestDTO
	 * @return
	 * Saves the search result user has visited. This helps
	 * build the history of user search request and result patterns. 
	 */
	@Path(SEARCH_HISTORY_REQUEST_URI)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(SearchResultCommonDTO requestDTO);
}
