/**
 * parshwabhoomi-server	12-Nov-2017:8:08:38 PM
 */
package org.cs.parshwabhoomi.server.rest.impl;

import java.util.List;

import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.core.SearchAggregator;
import org.cs.parshwabhoomi.server.core.SearchAggregatorImpl;
import org.cs.parshwabhoomi.server.dto.ErrorResponseDTO;
import org.cs.parshwabhoomi.server.dto.ErrorResponseDTO.HTTP_STATUS_CODE;
import org.cs.parshwabhoomi.server.dto.adapter.SearchResultResponseDTOAdapter;
import org.cs.parshwabhoomi.server.dto.adapter.SearchRquestDTOAdapter;
import org.cs.parshwabhoomi.server.dto.search.SearchRequestDTO;
import org.cs.parshwabhoomi.server.dto.search.SearchResultResponseDTO;
import org.cs.parshwabhoomi.server.model.SearchContext;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.rest.AbstractResource;
import org.cs.parshwabhoomi.server.rest.SearchResource;
import org.cs.parshwabhoomi.server.utils.RestUtils;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
@Path("/search")
public class SearchResourceImpl extends AbstractResource implements SearchResource {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.rest.SearchResource#search(org.cs.parshwabhoomi.server.dto.impl.SearchRequestDTO)
	 */
	@Override
	public Response search(SearchRequestDTO requestDTO) {
//		LogManager.getLogger().info("Auth scheme: "+getSecurityContext().getAuthenticationScheme());
//		LogManager.getLogger().info("Auth name: "+getSecurityContext().getUserPrincipal().getName());
		
		Response response = null;
		try{
			SearchRquestDTOAdapter adapter = new SearchRquestDTOAdapter();
			SearchContext context = adapter.buildRequest(requestDTO);
			
			SearchAggregator aggregator = new SearchAggregatorImpl();
			List<SearchResult> searchResults = aggregator.getResults(context);

			SearchResultResponseDTOAdapter responseDTOAdapter = new SearchResultResponseDTOAdapter();
			List<SearchResultResponseDTO> dtos = responseDTOAdapter.buildResponse(searchResults);
			GenericEntity<List<SearchResultResponseDTO>> gsr = new GenericEntity<List<SearchResultResponseDTO>>(dtos){};
			response = Response.ok(gsr).build();
		}catch(Exception e){
			ErrorResponseDTO errorResponseDTO = RestUtils.createErrorResponseDTO(HTTP_STATUS_CODE.INTERNAL_SERVER_ERROR, "Couldn't serve search results! Please try again.");
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorResponseDTO).build();
			LogManager.getLogger().error("Couldn't serve search results!", e);
		}
		
		return response;
	}
	

}
