/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.raw.impl.SearchDaoImpl;
import org.cs.parshwabhoomi.server.model.SearchResult;


/**
 *
 * @author gayatri
 */

public class Main {

    /**
     * @param args the command line arguments
     */
	
    public static void main(String[] args) {
    	SearchDaoImpl searchDaoImpl = (SearchDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("SearchDaoImpl");
     	List<SearchResult> results = searchDaoImpl.findByMatchingUserPref("apple", "saurabh");
     	LogManager.getLogger().info("Results found by matching user prefs= "+results.size());
     	results = searchDaoImpl.findByMatchingVendorOfferings("apple", "saurabh");
     	LogManager.getLogger().info("Results found by matching vendor offerings = "+results.size());
     	
     	List<String>modifiedSearchTerms = searchDaoImpl.getModifiedSearchTermByMatchingUserPref("apple", "saurabh");
     	LogManager.getLogger().info("Modified search terms found by matching user prefs= "+modifiedSearchTerms.size());
     	modifiedSearchTerms = searchDaoImpl.getModifiedSearchTermByMatchingUserPrefCategory("apple", "saurabh");
     	LogManager.getLogger().info("Modified search terms found by matching user prefs category= "+modifiedSearchTerms.size());
     	modifiedSearchTerms = searchDaoImpl.getModifiedSearchTermByMatchingVendorOfferingsCategory("apple", "saurabh");
     	LogManager.getLogger().info("Modified search terms found by matching vendor offerings category= "+modifiedSearchTerms.size());
     	searchDaoImpl.close();
     	
//    	Geofencer selector = new Geofencer((float)17.6759587, (float)75.3148885);
//    	BusinessVendorDaoImpl businessVendorDaoImpl = (BusinessVendorDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("BusinessVendorDaoImpl");
//    	List<BusinessVendor> vendors = businessVendorDaoImpl.getAll();
//    	vendors = selector.findWithinRadius(vendors);
    	
//    	GoogleMapsService mapsService = new GoogleMapsService();
//    	List<Float> latLng = mapsService.getGeocodedAddress("English Medium School, Rukmini Nagar, Pandharpur");
//    	LogManager.getLogger().info("Lat long = "+latLng);
    	
//    	SearchAggregator searchAggregator = new SearchAggregatorImpl();
//    	UserCredential userCredential = new UserCredential();
//    	userCredential.setUsername("saurabh");
//    	userCredential.setRole(Role.END_USER);
//    	SearchContext searchContext = new SearchContext(18.51657820F,73.84310780F, "apple", userCredential);
//    	List<SearchResult> searchResults = searchAggregator.getResults(searchContext);
//    	LogManager.getLogger().info("Main test class; results received for test search context count = "+searchResults.size());
        //List<SearchResultResponseDTO> dtos = googleSearchService.getResults("apple computers Computers Pulachi Wadi,Deccan Gymkhana,Pune");
    }

}
