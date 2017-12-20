/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.test;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.core.GoogleMapsService;
import org.cs.parshwabhoomi.server.core.SearchAggregator;
import org.cs.parshwabhoomi.server.core.SearchAggregatorImpl;
import org.cs.parshwabhoomi.server.model.SearchContext;
import org.cs.parshwabhoomi.server.model.SearchResult;
import org.cs.parshwabhoomi.server.model.UserCredential;
import org.cs.parshwabhoomi.server.model.UserCredential.Role;

/**
 *
 * @author gayatri
 */

public class Main {

    /**
     * @param args the command line arguments
     */
	
    public static void main(String[] args) {
    	GoogleMapsService mapsService = new GoogleMapsService();
    	List<Float> latLng = mapsService.getGeocodedAddress("English Medium School, Rukmini Nagar, Pandharpur");
    	LogManager.getLogger().info("Lat long = "+latLng);
    	
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
