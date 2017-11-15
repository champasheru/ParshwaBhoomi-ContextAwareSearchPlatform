/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.DBManager;
import org.cs.parshwabhoomi.server.dataparser.SearchResponseParser;
import org.cs.parshwabhoomi.server.dataparser.XMLSerializer;
import org.cs.parshwabhoomi.server.domainobjects.PBSearchResult;
import org.cs.parshwabhoomi.server.domainobjects.SearchResult;
import org.cs.parshwabhoomi.server.domainobjects.SearchServiceResult;
import org.cs.parshwabhoomi.server.dto.adapter.GoogleGeocodedAddressResponseDTOAdapter;
import org.cs.parshwabhoomi.server.dto.impl.SearchResultResponseDTO;

/**
 *
 * @author saurabh
 */
public class SearchService {
    //search vendor like Google/Yahoo/Bing etc.
    private String vendor;
    
    private static final String GOOGLE_CUSTOM_SEARCH_API_BASE_URI="https://www.googleapis.com/customsearch/v1?";
    private static final String GOOGLE_MAPS_GEOCODING_API_BASE_URI="https://maps.googleapis.com/maps/api/geocode/json?";
    
    
    public SearchService(String webSearchVendor){
        this.vendor=webSearchVendor;   
    }

   
    /************* V2 ************/

    /**
     * 
     * @param query
     * @return
     * @throws java.io.IOException
     * This method returns XML with search results in order:
     * 1.custom search results
     * 2.search service results
     */
    public String getSearchResultsXMLV2For(String query,String username,String latitude,String longitude) throws IOException{
        String address="Pune";
        if(latitude!=null && longitude!=null){
            String temp=getReverseGeocodedAddressFrom(latitude,longitude);
            if(temp!=null){
                System.out.println("[SearchService] address @ Reverse- geocoding= "+temp);
                address=temp;
            }
        }
        
        System.out.println("\n[SearchService] The Original search query : "+query);
        
        //BusinessEntity results
        ArrayList<SearchResult> searchResults=new ArrayList<SearchResult>();
        ArrayList<PBSearchResult> pBSearchResults=null;
        
        //Find the local vendors pertaining to the user's location.
        DBManager dbManager= DBManager.getDBManager();
        if(address.contains(",")){
            String[] addressComponents=address.split(",");
            if(addressComponents.length==2){
                pBSearchResults=dbManager.getSearchResultsFor(query,username,null,addressComponents[0],addressComponents[1]);
            }else if(addressComponents.length==3){
                pBSearchResults=dbManager.getSearchResultsFor(query,username,addressComponents[0],addressComponents[1],addressComponents[2]);
            }else if(addressComponents.length==4){
                pBSearchResults=dbManager.getSearchResultsFor(query,username,addressComponents[1],addressComponents[2],addressComponents[3]);
            }
        }else{
            pBSearchResults=dbManager.getSearchResultsFor(query,username,null,null,address);
        }
        
        
        if(pBSearchResults!=null && pBSearchResults.size()>0){
            for(int i=0;i<pBSearchResults.size();i++){
                pBSearchResults.get(i).setType(SearchResult.RESULT_VENDOR);
            }
            searchResults.addAll(pBSearchResults);
        }
        
        
        //UserCredential prefs + location results
        //Get the user prefs to build the user specific search query.
        ArrayList<String> userSpecificSearchQueries=dbManager.getUserSpecificSearchQueryForSearchService(query,username);
        if(userSpecificSearchQueries!=null && userSpecificSearchQueries.size()>0){
            for(int i=0;i<userSpecificSearchQueries.size();i++){
                String aUserSpecificSearchQuery=userSpecificSearchQueries.get(i)+" "+address;
                System.out.println("_The UserCredential specific search query "+i+": "+aUserSpecificSearchQuery);
                
                ArrayList<SearchServiceResult> searchServiceResults = getSearchResultsWithGoogleCustomSearch(aUserSpecificSearchQuery);
                if (searchServiceResults != null && searchServiceResults.size() > 0) {
                    for(int j=0;j<searchServiceResults.size();j++){
                        searchServiceResults.get(j).setType(SearchResult.RESULT_USERPREF_LOCATION);
                    }
                    searchResults.addAll(searchServiceResults);
                    System.out.println("[SearchService] UserCredential specific query result count "+i+": "+searchServiceResults.size());
                }
            }
        }
        
        
        //Query + location; No user prefs
        String modifiedQuery=query+" "+address;
        System.out.println("[SearchService] Modified query for location: "+modifiedQuery);
        
        //Then get the search results for the original search query pertaining to this device location.
        ArrayList<SearchServiceResult> searchServiceResults= getSearchResultsWithGoogleCustomSearch(modifiedQuery);
        if(searchServiceResults!=null && searchServiceResults.size()>0){
            for(int i=0;i<searchServiceResults.size();i++){
                searchServiceResults.get(i).setType(SearchResult.RESULT_LOCATION);
            }
            searchResults.addAll(searchServiceResults);
            System.out.println("[SearchService] Location based query result count:"+searchServiceResults.size());
        }
        
        
        //Query results
        //Default search service results irrespective of location.
        System.out.println("[SearchService] Getting the search result for the original query...");
        searchServiceResults= getSearchResultsWithGoogleCustomSearch(query);
        if(searchServiceResults!=null && searchServiceResults.size()>0){
            for(int i=0;i<searchServiceResults.size();i++){
                searchServiceResults.get(i).setType(SearchResult.RESULT_GENERAL);
            }
            searchResults.addAll(searchServiceResults);
            System.out.println("[SearchService] Original query result count:"+searchServiceResults.size());
        }
        
        
        //So in the returned XML,the order of search results wd be-
        //1.Vendor results
        //2.User preference specific results from search service
        //3.default search results for the search query submitted by the user in Pune.
        //4.default search results for the search query submitted by the user in general(not specific to any location).
               
        return XMLSerializer.serialize(searchResults);
    }
    
    
    public ArrayList<SearchServiceResult> getSearchResultsWithGoogleCustomSearch(String query) throws IOException{
    	System.out.println("Using Google Custom Search API Service...\n");
        InputStream is=null;
        ArrayList<SearchServiceResult> searchServiceResult=null;
        try {
        	String temp=GOOGLE_CUSTOM_SEARCH_API_BASE_URI+
        				"key="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_API_KEY)
        				+"&cx="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_CSE_ID)
        				+"&q="+URLEncoder.encode(query,"UTF-8");
            URL tempUrl = new URL(temp);
            HttpURLConnection conn=(HttpURLConnection)tempUrl.openConnection();
            conn.connect();
            is=conn.getInputStream();

            System.out.println("[Google Search Response]\n");
            BufferedReader br= new BufferedReader(new InputStreamReader(is)); 
            StringBuilder builder=new StringBuilder();
            String aLine=null;
            while((aLine=br.readLine())!=null){
                System.out.println(aLine);
                builder.append(aLine);
            }
            System.out.println("[End ofResponse]\n");
            
            SearchResponseParser responseParser= new SearchResponseParser();
            searchServiceResult=responseParser.parseJsonV2(builder.toString());
        } catch (MalformedURLException ex) {
            System.out.println("[SearchService] Google Invalid URL: \n");
        }finally{
            if(is!=null)
                is.close();
        } 
        return searchServiceResult;
    }
    
    
  //Use the Google Maps API -Reverse Geocoding service & get the formatted,human readable address for the corresponding
    //(lat,long) pair.
    private String getReverseGeocodedAddressFrom(String latitude,String longitude){
        InputStream is=null;
        String address=null;
        try {
        	String temp=GOOGLE_MAPS_GEOCODING_API_BASE_URI+
        				"latlng="+latitude+","+longitude
        				+"&key="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_API_KEY);
            System.out.println("[SearchService] The geocoding URL= "+temp);
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
            System.out.println("[SearchService] Invalid URL: \n");
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


