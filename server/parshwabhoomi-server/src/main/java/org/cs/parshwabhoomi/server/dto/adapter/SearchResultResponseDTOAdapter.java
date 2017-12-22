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
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class SearchResultResponseDTOAdapter {
	//The base url for all results whose type is PB_*
	private String baseUrl;
	
	/**
	 * 
	 */
	public SearchResultResponseDTOAdapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchResultResponseDTOAdapter(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public List<SearchResultResponseDTO> buildResponse(List<SearchResult> searchResults){
		LogManager.getLogger().info("Building seach response DTO list....");
		List<SearchResultResponseDTO> dtos = new ArrayList<>(searchResults.size());
		for(SearchResult searchResult : searchResults){
			dtos.add(buildResponse(searchResult));
		}
		return dtos;
	}
	
	
	public SearchResultResponseDTO buildResponse(SearchResult searchResult){
//		LogManager.getLogger().info("Building single seach response DTO....");
		
		SearchResultResponseDTO dto = new SearchResultResponseDTO();
		
		if(searchResult.getType().name().startsWith("TYPE_PB_")){
			String url = baseUrl+searchResult.getVendorID();
			dto.setLink(url);
			dto.setDisplayLink(url);
			dto.setFormattedUrl(url);
			dto.setHtmlFormattedUrl(url);
		}else{
			dto.setLink(searchResult.getLink());
			dto.setDisplayLink(searchResult.getDisplayLink());
			dto.setFormattedUrl(searchResult.getFormattedUrl());
			dto.setHtmlFormattedUrl(searchResult.getHtmlFormattedUrl());
		}
		
		dto.setImageUrl(searchResult.getImageUrl());
		
		dto.setHtmlSnippet(searchResult.getHtmlSnippet());
		dto.setHtmlTitle(searchResult.getHtmlTitle());
		dto.setSnippet(searchResult.getSnippet());
		dto.setTitle(searchResult.getTitle());
		dto.setTagline(searchResult.getTagline());
		dto.setType(searchResult.getType().name());
		dto.setProvider(searchResult.getProvider().name());
		if(searchResult.getCategory() != null){
			dto.setBusinessCategory(searchResult.getCategory().name());
		}
		dto.setVendorId(searchResult.getVendorID());
		return dto;
	}
}
