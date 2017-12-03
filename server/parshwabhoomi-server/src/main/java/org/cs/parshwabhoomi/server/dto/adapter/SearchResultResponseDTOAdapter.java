/**
 * parshwabhoomi-server	03-Dec-2017:3:29:29 PM
 */
package org.cs.parshwabhoomi.server.dto.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dto.search.SearchResultResponseDTO;
import org.cs.parshwabhoomi.server.model.SearchResult;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class SearchResultResponseDTOAdapter {
	public List<SearchResultResponseDTO> buildResponse(List<SearchResult> searchResults){
		LogManager.getLogger().info("Building seach response DTO list....");
		List<SearchResultResponseDTO> dtos = new ArrayList<>(searchResults.size());
		for(SearchResult searchResult : searchResults){
			dtos.add(buildResponse(searchResult));
		}
		return dtos;
	}
	
	
	public SearchResultResponseDTO buildResponse(SearchResult searchResult){
		LogManager.getLogger().info("Building single seach response DTO....");
		SearchResultResponseDTO dto = new SearchResultResponseDTO();
		dto.setDisplayLink(searchResult.getDisplayLink());
		dto.setFormattedUrl(searchResult.getFormattedUrl());
		dto.setHtmlFormattedUrl(searchResult.getHtmlFormattedUrl());
		dto.setHtmlSnippet(searchResult.getHtmlSnippet());
		dto.setHtmlTitle(searchResult.getHtmlTitle());
		dto.setImageUrl(searchResult.getImageUrl());
		dto.setLink(searchResult.getLink());
		dto.setSnippet(searchResult.getSnippet());
		dto.setTitle(searchResult.getTitle());
		dto.setType(searchResult.getType().name());
		dto.setProvider(searchResult.getProvider().name());
		dto.setBusinessCategory(searchResult.getCategory());
		return dto;
	}
}
