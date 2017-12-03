/**
 * parshwabhoomi-server	12-Nov-2017:8:13:44 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.raw.impl.SearchDaoImpl;
import org.cs.parshwabhoomi.server.dto.adapter.GoogleGeocodedAddressResponseDTOAdapter;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.SearchContext;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.model.SearchResult.Type;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class SearchAggregatorImpl implements SearchAggregator {
    private static final String GOOGLE_MAPS_GEOCODING_API_BASE_URI="https://maps.googleapis.com/maps/api/geocode/json?";
    
    private SearchContext searchContext;
    private Address address;
    private static final String DEFAULT_ADDRESS = "Pune";
    
    

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
        	address = getReverseGeocodedAddressFrom(searchContext.getLatitude(), searchContext.getLongitude());
        }else{
        	address = new Address();
        	address.setLocality(DEFAULT_ADDRESS);
        }
        
        List<SearchResult> aggregatedSearchResults = new ArrayList<>();
        aggregatedSearchResults.addAll(getLocalSearchResults());
        aggregatedSearchResults.addAll(getSearchServiceResultsByUserPrefsAndLocation());
        aggregatedSearchResults.addAll(getSearchServiceResultsByLocation());
        aggregatedSearchResults.addAll(getSearchServiceResultsByOriginalQuerySansLocation());
        
		return aggregatedSearchResults;
	}
	
	
	//Find the local vendors pertaining to the user's context/location.
	private List<SearchResult> getLocalSearchResults(){
		LogManager.getLogger().info("Building search service results using local platform listings...");
        SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
        List<SearchResult> localSearchResults = searchDaoImpl.getSearchResultsFor(
        											 	searchContext.getQuery() ,
        												searchContext.getUserCredential().getUsername(),
        												address.getRouteOrLane(),
        												address.getSublocality(),
        												address.getLocality()
        												);
        searchDaoImpl.close();
        //TODO: shall have more classifiers: TYPE_PB_LOCATION and/or TYPE_PB_GENERAL
        for(SearchResult result : localSearchResults){
        	result.setType(Type.TYPE_PB_PREFERRED);
        }
        return localSearchResults;
	}
	
	
	//Results from configured search service/engine using user prefs and location/address provided/derived from searchContext.
	private List<SearchResult> getSearchServiceResultsByUserPrefsAndLocation(){
		LogManager.getLogger().info("Building search service results using search service with User prefs and Location...");
		
		GoogleSearchService googleSearchService = new GoogleSearchService();
		List<SearchResult> userPrefsAndLocationBasesResults = new ArrayList<>();
		
		//UserCredential prefs + location results
        //Get the user prefs to build the user specific search query.
		SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
        ArrayList<String> userSpecificSearchQueries = searchDaoImpl.getUserSpecificSearchQueryForSearchService(
        												searchContext.getQuery() ,
        												searchContext.getUserCredential().getUsername());
        searchDaoImpl.close();
        
        if(userSpecificSearchQueries != null && userSpecificSearchQueries.size() > 0){
            for(int i = 0;i < userSpecificSearchQueries.size(); i++){
                String userSpecificSearchQuery = userSpecificSearchQueries.get(i)+" "+address.getRouteOrLane()+","+address.getSublocality()+","+address.getLocality();
                LogManager.getLogger().info("The User pref specific search query "+i+": "+userSpecificSearchQuery);
                
                List<SearchResult> searchServiceResults = googleSearchService.getResults(userSpecificSearchQuery);
                if (searchServiceResults != null && searchServiceResults.size() > 0) {
                    for(int j = 0; j < searchServiceResults.size(); j++){
                        searchServiceResults.get(j).setType(Type.TYPE_SEARCH_ENGINE_PREFERRED);
                    }
                    userPrefsAndLocationBasesResults.addAll(searchServiceResults);
                    LogManager.getLogger().info("User prefs and location specific query result count "+i+": "+searchServiceResults.size());
                }
            }
        }
        
        return userPrefsAndLocationBasesResults;
	}
	
	
	//Results from configured search service/engine using original query and location/address provided/derived from searchContext.
	private List<SearchResult> getSearchServiceResultsByLocation(){
		LogManager.getLogger().info("Building search service results using search service with original user query and Location...");
		
		//Query + location; No user prefs
        String modifiedQuery = searchContext.getQuery()+" "+address.getRouteOrLane()+","+address.getSublocality()+","+address.getLocality();
        LogManager.getLogger().info("Modified query for location: "+modifiedQuery);
        
        GoogleSearchService googleSearchService = new GoogleSearchService();
        //Then get the search results for the original search query pertaining to this device location.
        List<SearchResult> locationSpecificSearchResults = googleSearchService.getResults(modifiedQuery);
        if(locationSpecificSearchResults != null && locationSpecificSearchResults.size() > 0){
            for(SearchResult searchResult : locationSpecificSearchResults){
            	searchResult.setType(Type.TYPE_SEARCH_ENGINE_LOCATION);
            }
            LogManager.getLogger().info("Location based query result count:"+locationSpecificSearchResults.size());
        }
        
        return locationSpecificSearchResults;
	}
	
	
	
	//Results from configured search service/engine using original query and without the location.
	private List<SearchResult> getSearchServiceResultsByOriginalQuerySansLocation(){
		LogManager.getLogger().info("Building search service results using search service with original user query and without the location...");
		
		//Query results
        //Default search service results irrespective of location.
		GoogleSearchService googleSearchService = new GoogleSearchService();
        List<SearchResult> searchResults = googleSearchService.getResults(searchContext.getQuery());
        if(searchResults != null && searchResults.size() >0){
            for(SearchResult searchResult : searchResults){
            	searchResult.setType(Type.TYPE_SEARCH_ENGINE_GENERAL);
            }
            LogManager.getLogger().info("Original query search result count:"+searchResults.size());
        }
        
        return searchResults;
	}
	
	
    //Use the Google Maps API -Reverse Geocoding service & get the formatted,human readable address for the corresponding
    //(lat,long) pair.
    private Address getReverseGeocodedAddressFrom(float latitude, float longitude){
        InputStream is = null;
        Address address = null;
        
        try {
        	String temp=GOOGLE_MAPS_GEOCODING_API_BASE_URI+
        				"latlng="+latitude+","+longitude
        				+"&key="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_API_KEY);
            System.out.println("[DefaultSearchService] The geocoding URL= "+temp);
            URL tempURL = new URL(temp);
            HttpURLConnection conn=(HttpURLConnection)tempURL.openConnection();
            conn.connect();
            
            is=conn.getInputStream();
            BufferedReader br= new BufferedReader(new InputStreamReader(is)); 
            System.out.println("[Address Response]\n");
                
            StringBuilder builder=new StringBuilder();
            String aLine=null;
            while((aLine=br.readLine())!=null){
                System.out.println(aLine);
                builder.append(aLine);
            }
            System.out.println("[End ofResponse]\n");
            
            return GoogleGeocodedAddressResponseDTOAdapter.parseJsonForAddress(builder.toString());
            
        } catch (MalformedURLException ex) {
            System.out.println("[DefaultSearchService] Invalid URL: \n");
        } catch (IOException ioe) {
            ioe.printStackTrace(System.out);
        }finally{
            try{
                if(is!=null){
                    is.close();
                }
            } catch (IOException ioe){
                
            }
        }
        
        return address;
    }
}
