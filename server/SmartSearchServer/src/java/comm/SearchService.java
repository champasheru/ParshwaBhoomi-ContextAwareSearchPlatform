/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package comm;

import database.DBManager;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStream;
import java.net.URLEncoder;

import dataparser.SearchResponseParser;
import dataparser.XMLSerializer;
import datastore.CustomSearchResult;
import datastore.SearchResult;
import datastore.SearchServiceResult;
import datastore.Utils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.HmacSha1MessageSigner;


/**
 *
 * @author saurabh
 */
public class SearchService {
    //search vendor like Google/Yahoo/Bing etc.
    private String vendor;
    
    //Currently this is using the Yahoo WebSearch Service REST implementation.
    //This needs a unique app id which is generated as below
    private static final String yahooAppId="ai._zLbV34Hd6CvpAu5Tu08g4gvvL3K3h_0p5bETeW.kkRKG4zv45eSzuMTnwdLKNg--";
                                            
    private static final String consumerKey="dj0yJmk9WlB1R3FkS01IUDBMJmQ9WVdrOWRXRlZRemRJTm1zbWNHbzlNVGN6T1RZeU5qZzJNZy0tJnM9Y29uc3VtZXJzZWNyZXQmeD1hMA--"; 
    
    private static final String consumerSecret="5dcb5b44b82c8b3238158cf2b52b65a5282ba6d7";

    //default no of search results to ask the search service to return.
    private int numYahooResults=20;
    
    private String yahooSearchUrlV1="http://api.search.yahoo.com/WebSearchService/V1/webSearch?yahooAppId="+yahooAppId+"&results="+numYahooResults+"&query=";
    
    private String yahooSearchUrlV2="http://yboss.yahooapis.com/ysearch/web?q=";
    
    private String googleGeocodingUrl="http://maps.googleapis.com/maps/api/geocode/json?sensor=false&latlng=";
    
    //rsz param indicates that 8 results be returned. Google doc mandates betn 1-8.
    private String googleSearchUrl="http://ajax.googleapis.com/ajax/services/search/web?v=1.0&rsz=8&q=";
    
    public SearchService(String webSearchVendor){
        this.vendor=webSearchVendor;   
    }

   

    /**
     * 
     * @param query
     * @return
     * @throws java.io.IOException
     * This method returns XML with search results in order:
     * 1.custom search results
     * 2.search service results
     */
    public String getSearchResultsXMLFor(String query,String username,String latitude,String longitude) throws IOException{
        String address="Pune";
        if(latitude!=null && longitude!=null){
            String temp=getAddressFrom(latitude,longitude);
            if(temp!=null){
                System.out.println("[SearchService] address @ Reverse- geocoding= "+temp);
                address=temp;
            }
        }
        
        System.out.println("\n[SearchService] The Original search query : "+query);
        
        //Vendor results
        ArrayList<SearchResult> searchResults=new ArrayList<SearchResult>();
        ArrayList<CustomSearchResult> customSearchResults=null;
        
        //Find the local vendors pertaining to the user's location.
        DBManager dbManager= DBManager.getDBManager();
        if(address.contains(",")){
            String[] addressComponents=address.split(",");
            if(addressComponents.length==2){
                customSearchResults=dbManager.getSearchResultsFor(query,username,null,addressComponents[0],addressComponents[1]);
            }else if(addressComponents.length==3){
                customSearchResults=dbManager.getSearchResultsFor(query,username,addressComponents[0],addressComponents[1],addressComponents[2]);
            }else if(addressComponents.length==4){
                customSearchResults=dbManager.getSearchResultsFor(query,username,addressComponents[1],addressComponents[2],addressComponents[3]);
            }
        }else{
            customSearchResults=dbManager.getSearchResultsFor(query,username,null,null,address);
        }
        
        
        if(customSearchResults!=null && customSearchResults.size()>0){
            for(int i=0;i<customSearchResults.size();i++){
                customSearchResults.get(i).setType(SearchResult.RESULT_VENDOR);
            }
            searchResults.addAll(customSearchResults);
        }
        
        
        //User prefs + location results
        //Get the user prefs to build the user specific search query.
        ArrayList<String> userSpecificSearchQueries=dbManager.getUserSpecificSearchQueryForSearchService(query,username);
        if(userSpecificSearchQueries!=null && userSpecificSearchQueries.size()>0){
            for(int i=0;i<userSpecificSearchQueries.size();i++){
                String aUserSpecificSearchQuery=userSpecificSearchQueries.get(i)+" "+address;
                System.out.println("_The User specific search query "+i+": "+aUserSpecificSearchQuery);
                
                ArrayList<SearchServiceResult> searchServiceResults = getSearchResultsWithYahooBossV2(aUserSpecificSearchQuery);
                if (searchServiceResults != null && searchServiceResults.size() > 0) {
                    for(int j=0;j<searchServiceResults.size();j++){
                        searchServiceResults.get(j).setType(SearchResult.RESULT_USERPREF_LOCATION);
                    }
                    searchResults.addAll(searchServiceResults);
                    System.out.println("[SearchService] User specific query result count "+i+": "+searchServiceResults.size());
                } 
            }
        }
        
        
        //Query + location; No user prefs
        String modifiedQuery=query+" "+address;
        System.out.println("[SearchService] Modified query for location: "+modifiedQuery);
        
        //Then get the search results for the original search query pertaining to this device location.
        ArrayList<SearchServiceResult> searchServiceResults= getSearchResultsWithYahooBossV2(modifiedQuery);
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
        searchServiceResults= getSearchResultsWithYahooBossV2(query);
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
    
    
    //Uses the Yahoo V1 search service;free usage
    public ArrayList<SearchServiceResult> getSearchResultsWithYahooV1(String query) throws IOException{
        InputStream is=null;
        ArrayList<SearchServiceResult> searchServiceResults=null;
        try {
            String temp=yahooSearchUrlV1+URLEncoder.encode(query,"UTF-8");
            URL searchUrl = new URL(temp);
            HttpURLConnection conn=(HttpURLConnection)searchUrl.openConnection();
            conn.connect();
            is=conn.getInputStream();

            SearchResponseParser responseParser= new SearchResponseParser();
            searchServiceResults=responseParser.parseResponse(is);
        } catch (MalformedURLException ex) {
            System.out.println("[SearchService] Yahoo V1 Invalid URL: \n"+ex);
        }finally{
            if(is!=null)
                is.close();
        }
        return searchServiceResults;
    }
    
    
    //The paid V2 version of Yahoo webservice based on BOSS platform. Uses the OAuth for API usage authentication.
    //Every query request needs to be provided with the API/Consumer key & secret.
    public ArrayList<SearchServiceResult> getSearchResultsWithYahooBossV2(String query) throws IOException{
        InputStream is=null;
        ArrayList<SearchServiceResult> searchServiceResults=null;
        try {
            String temp=yahooSearchUrlV2+URLEncoder.encode(query, "UTF-8")+"&format=xml";
            temp=temp.replaceAll("\\+", "%20");//gives 401 unautherized response if the query encoded with + for space.
           
            URL searchUrl = new URL(temp);
            HttpURLConnection conn=(HttpURLConnection)searchUrl.openConnection();
            OAuthConsumer consumer = new DefaultOAuthConsumer(consumerKey, consumerSecret);
            consumer.sign(conn);
            conn.connect();
            is=conn.getInputStream();
            
            SearchResponseParser responseParser= new SearchResponseParser();
            searchServiceResults=responseParser.parseResponse(is);
        } catch (OAuthMessageSignerException ex) {
            System.out.println("[SearchService] OAuthMessageSignerException: \n"+ex);
        } catch (OAuthExpectationFailedException ex) {
            System.out.println("[SearchService] OAuthExpectationFailedException: \n"+ex);
        } catch (OAuthCommunicationException ex) {
            System.out.println("[SearchService] OAuthCommunicationException: \n"+ex);
        } catch (MalformedURLException ex) {
            System.out.println("[SearchService] Yahoo V2 Invalid URL: \n"+ex);
        }finally{
            if(is!=null)
                is.close();
        }
        return searchServiceResults;
    }
    
    
    public ArrayList<SearchServiceResult> getSearchResultsWithGoogle(String query) throws IOException{
        InputStream is=null;
        ArrayList<SearchServiceResult> searchServiceResult=null;
        try {
            String temp=googleSearchUrl+URLEncoder.encode(query,"UTF-8");
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
            searchServiceResult=responseParser.parseJson(builder.toString());
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
    private String getAddressFrom(String latitude,String longitude){
        InputStream is=null;
        String address=null;
        try {
            String temp=googleGeocodingUrl+latitude+","+longitude;
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
            
            return Utils.parseJsonForAddress(builder.toString());
            
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


