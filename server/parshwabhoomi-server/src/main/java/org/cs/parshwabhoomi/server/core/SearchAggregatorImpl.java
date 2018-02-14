/**
 * parshwabhoomi-server	12-Nov-2017:8:13:44 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.raw.impl.SearchDaoImpl;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.BusinessCategory;
import org.cs.parshwabhoomi.server.model.SearchContext;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.model.SearchResult.Type;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class SearchAggregatorImpl implements SearchAggregator {
    private SearchContext searchContext;
    private Address address;
    private static final String DEFAULT_ADDRESS = "Pandharpur";
    private Map<String, String> categories = new HashMap<>();

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.core.SearchAggregator#getResults(org.cs.parshwabhoomi.server.model.SearchContext)
	 */
	@Override
	public List<SearchResult> getResults(SearchContext searchContext) {
		LogManager.getLogger().info("[Start]");
		LogManager.getLogger().info("Original search query = "+searchContext.getQuery().trim());
		
		this.searchContext = searchContext;
		
		LogManager.getLogger().info("Resolving the address from the search context...");
        if(searchContext.getLatitude() > 0 && searchContext.getLongitude() > 0){
        	GoogleMapsService mapsService = new GoogleMapsService();
        	address = mapsService.getReverseGeocodedAddress(searchContext.getLatitude(), searchContext.getLongitude());
        }else{
        	address = new Address();
        	address.setLocality(DEFAULT_ADDRESS);
        }
        
        List<SearchResult> aggregatedSearchResults = new ArrayList<>();
        
        //local
        aggregatedSearchResults.addAll(localSearchResultsByMatchingUserPrefWithinFence());
        aggregatedSearchResults.addAll(localSearchResultsByMatchingUserPrefOutsideFence());
        aggregatedSearchResults.addAll(localSearchResultsByMatchingVendorOfferingsWithinFence());
        aggregatedSearchResults.addAll(localSearchResultsByMatchingVendorOfferingsOutsideFence());
        
        //search service
        aggregatedSearchResults.addAll(searchServiceResultsByUserPrefsAndLocation());
        aggregatedSearchResults.addAll(searchServiceResultsByUserPrefCategoryAndLocation());
        aggregatedSearchResults.addAll(searchServiceResultsByVendorBusinessCategoryAndLocation());
        aggregatedSearchResults.addAll(searchServiceResultsByOriginalSearchTermAndLocation());
        aggregatedSearchResults.addAll(searchServiceResultsByOriginalSearchTerm());
        
		return aggregatedSearchResults;
	}
	
	
	private List<SearchResult> localSearchResultsByMatchingUserPrefWithinFence(){
		LogManager.getLogger().info("Retrieving local results matching user pref and within fence...");
        
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
        List<SearchResult> results = searchDaoImpl.findByMatchingUserPref(searchContext.getQuery(), searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        Geofencer geofencer = new Geofencer(searchContext.getLatitude(), searchContext.getLongitude());
        results = geofencer.findWithinFence(results);
        for(SearchResult result : results){
        	result.setType(Type.TYPE_PB_PREFERRED);
        }
        return results;
	}
	
	
	private List<SearchResult> localSearchResultsByMatchingUserPrefOutsideFence(){
		LogManager.getLogger().info("Retrieving local results matching user pref but outside fence...");
        
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
        List<SearchResult> results = searchDaoImpl.findByMatchingUserPref(searchContext.getQuery(), searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        Geofencer geofencer = new Geofencer(searchContext.getLatitude(), searchContext.getLongitude());
        results = geofencer.findOutsideFence(results);
        for(SearchResult result : results){
        	result.setType(Type.TYPE_PB_PREFERRED_LOCATION);
        }
        return results;
	}
	
	
	private List<SearchResult> localSearchResultsByMatchingVendorOfferingsWithinFence(){
		LogManager.getLogger().info("Retrieving local results matching vendor offerings and within fence...");
        
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
        List<SearchResult> results = searchDaoImpl.findByMatchingVendorOfferings(searchContext.getQuery(), searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        Geofencer geofencer = new Geofencer(searchContext.getLatitude(), searchContext.getLongitude());
        results = geofencer.findWithinFence(results);
        for(SearchResult result : results){
        	result.setType(Type.TYPE_PB_LOCATION);
        }
        return results;
	}
	
	
	private List<SearchResult> localSearchResultsByMatchingVendorOfferingsOutsideFence(){
		LogManager.getLogger().info("Retrieving local results matching vendor offerings but outside fence...");
        
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
        List<SearchResult> results = searchDaoImpl.findByMatchingVendorOfferings(searchContext.getQuery(), searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        Geofencer geofencer = new Geofencer(searchContext.getLatitude(), searchContext.getLongitude());
        results = geofencer.findOutsideFence(results);
        for(SearchResult result : results){
        	result.setType(Type.TYPE_PB_GENERAL);
        }
        return results;
	}
	
	
	/**
	 * Results from configured search service/engine using user prefs and location/address provided/derived from searchContext.
	 * @return
	 */
	private List<SearchResult> searchServiceResultsByUserPrefsAndLocation(){
		LogManager.getLogger().info("Building search service results using User prefs and Location...");
		
		GoogleSearchService googleSearchService = new GoogleSearchService();
		List<SearchResult> results = new ArrayList<>();
		
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
		List<String> modifiedSearchTerms = searchDaoImpl.getModifiedSearchTermByMatchingUserPref(
															searchContext.getQuery(),
															searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        for(String term : modifiedSearchTerms){
        	String[] parts = term.split("::");
        	assert(parts.length == 2);
        	
        	term = parts[0]+" "+address.getLocality();
        	LogManager.getLogger().info("The modified User pref search term with location:"+term);
        	List<SearchResult> searchServiceResults = googleSearchService.getResults(term);
        	for(SearchResult result: searchServiceResults){
        		result.setCategory(BusinessCategory.valueOf(parts[1]));
        		result.setType(Type.TYPE_SEARCH_ENGINE_PREFERRED);
        		results.add(result);
        	}
        }
        
        LogManager.getLogger().info("Search service results found for user prefs and location:"+results.size());
        return results;
	}
	
	
	/**
	 * Results from configured search service/engine using user pref's business category and 
	 * location/address provided/derived from searchContext.
	 * @return
	 */
	private List<SearchResult> searchServiceResultsByUserPrefCategoryAndLocation(){
		LogManager.getLogger().info("Building search service results using User pref category and Location...");
		
		GoogleSearchService googleSearchService = new GoogleSearchService();
		List<SearchResult> results = new ArrayList<>();
		
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
		List<String> modifiedSearchTerms = searchDaoImpl.getModifiedSearchTermByMatchingUserPrefCategory(
															searchContext.getQuery(),
															searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        for(String term : modifiedSearchTerms){
        	String cateogry = term;
        	categories.put(term, term);
        	
        	term = term+" "+address.getLocality();
        	LogManager.getLogger().info("The modified User pref category search term with location "+term);
        	List<SearchResult> searchServiceResults = googleSearchService.getResults(term);
        	for(SearchResult result: searchServiceResults){
        		result.setCategory(BusinessCategory.valueOf(cateogry));
        		result.setType(Type.TYPE_SEARCH_ENGINE_PREFERRED);
        		results.add(result);
        	}
        }
        
        LogManager.getLogger().info("Search service results found for user prefs category and location:"+results.size());
        return results;
	}
	
	
	
	/**
	 * Results from configured search service/engine using vendor's business category and 
	 * location/address provided/derived from searchContext.
	 * @return
	 */
	private List<SearchResult> searchServiceResultsByVendorBusinessCategoryAndLocation(){
		LogManager.getLogger().info("Building search service results using Business category and Location...");
		
		GoogleSearchService googleSearchService = new GoogleSearchService();
		List<SearchResult> results = new ArrayList<>();
		
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
		List<String> modifiedSearchTerms = searchDaoImpl.getModifiedSearchTermByMatchingVendorOfferingsCategory(
															searchContext.getQuery(),
															searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        for(String term : modifiedSearchTerms){
        	if(categories.get(term) == null){
        		//It might happen that ByMatchingUserPrefCategory and ByMatchingVendorOfferingsCategory return the same category.
        		//In such case, we dont want to call the Google service again as the results are already fetched.
        		String category = term;
        		categories.put(term, term);
        		term = term+" "+address.getLocality();
            	LogManager.getLogger().info("The modified Vendor business category search term with location "+term);
            	List<SearchResult> searchServiceResults = googleSearchService.getResults(term);
            	for(SearchResult result: searchServiceResults){
            		result.setCategory(BusinessCategory.valueOf(category));
            		result.setType(Type.TYPE_SEARCH_ENGINE_PREFERRED);
            		results.add(result);
            	}
        	}
        }
        
        LogManager.getLogger().info("Search service results found for vendor business category and location:"+results.size());
        return results;
	}
	
	
	/**
	 * Results from configured search service/engine using original search term and 
	 * location/address provided/derived from searchContext.
	 * @return
	 */
	private List<SearchResult> searchServiceResultsByOriginalSearchTermAndLocation(){
		LogManager.getLogger().info("Building search service results using Original search term and Location...");
		
		GoogleSearchService googleSearchService = new GoogleSearchService();
		String term = searchContext.getQuery().trim()+" "+address.getLocality();
		List<SearchResult> searchServiceResults = googleSearchService.getResults(term);
		for(SearchResult result: searchServiceResults){
    		result.setType(Type.TYPE_SEARCH_ENGINE_LOCATION);
    	}
        LogManager.getLogger().info("Search service results found for original search query and location:"+searchServiceResults.size());
        return searchServiceResults;
	}
	
	
	/**
	 * Results from configured search service/engine using original search term but WITHOUT location
	 * @return
	 */
	private List<SearchResult> searchServiceResultsByOriginalSearchTerm(){
		LogManager.getLogger().info("Building search service results using Original search term...");
		
		GoogleSearchService googleSearchService = new GoogleSearchService();
		List<SearchResult> searchServiceResults = googleSearchService.getResults(searchContext.getQuery().trim());
		for(SearchResult result: searchServiceResults){
    		result.setType(Type.TYPE_SEARCH_ENGINE_GENERAL);
    	}
		
        LogManager.getLogger().info("Search service results found for original search query:"+searchServiceResults.size());
        return searchServiceResults;
	}
}
