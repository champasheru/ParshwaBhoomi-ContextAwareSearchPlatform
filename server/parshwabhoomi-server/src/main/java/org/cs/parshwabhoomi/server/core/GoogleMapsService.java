/**
 * parshwabhoomi-server	20-Dec-2017:3:18:51 PM
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
import org.cs.parshwabhoomi.server.dto.adapter.GoogleMapsAddressResponseDTOAdapter;
import org.cs.parshwabhoomi.server.model.Address;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class GoogleMapsService {
	private static final String GOOGLE_MAPS_GEOCODING_API_BASE_URI="https://maps.googleapis.com/maps/api/geocode/json?";
	
	/**
	 * @param latitude
	 * @param longitude
	 * @return the reverse geocoded, formatted,human readable address for the corresponding (lat,long) pair.
	 * Uses the Google Maps API.
	 */
    public Address getReverseGeocodedAddress(float latitude, float longitude){
        InputStream is = null;
        Address address = null;
        
        try {
        	String temp = GOOGLE_MAPS_GEOCODING_API_BASE_URI+
        				"latlng="+latitude+","+longitude
        				+"&key="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_API_KEY);
            
        	LogManager.getLogger().info("The reverse-geocoding URL= "+temp);
            
        	URL tempURL = new URL(temp);
            HttpURLConnection conn=(HttpURLConnection)tempURL.openConnection();
            conn.connect();
            
            is=conn.getInputStream();
            BufferedReader br= new BufferedReader(new InputStreamReader(is)); 
            LogManager.getLogger().info("[Address Response]\n");
                
            StringBuilder builder=new StringBuilder();
            String aLine=null;
            while((aLine=br.readLine())!=null){
                System.out.println(aLine);
                builder.append(aLine);
            }
            LogManager.getLogger().info("[End ofResponse]\n");
            
            return GoogleMapsAddressResponseDTOAdapter.getReverseGeocodedAddress(builder.toString());
            
        } catch (MalformedURLException ex) {
        	LogManager.getLogger().error("Invalid URL: \n");
        } catch (IOException ioe) {
        	LogManager.getLogger().error("Couldn't reverse geo-code the address!", ioe);
        }finally{
            try{
                if(is!=null){
                    is.close();
                }
            } catch (IOException ioe){
            	LogManager.getLogger().error("Couldn't reverse geo-code the address!", ioe);
            }
        }
        
        return address;
    }
    
    
    public List<Float> getGeocodedAddress(String address){
    	List<Float> latLong = new ArrayList<>(2);
    	InputStream is = null;
        try {
        	String temp = GOOGLE_MAPS_GEOCODING_API_BASE_URI+
        				"address="+URLEncoder.encode(address, "UTF-8")
        				+"&key="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_API_KEY);
            
        	LogManager.getLogger().info("The geocoding URL= "+temp);
        	
            URL tempURL = new URL(temp);
            HttpURLConnection conn=(HttpURLConnection)tempURL.openConnection();
            conn.connect();
            
            is=conn.getInputStream();
            BufferedReader br= new BufferedReader(new InputStreamReader(is)); 
            LogManager.getLogger().info("[Address Response]\n");
                
            StringBuilder builder=new StringBuilder();
            String aLine=null;
            while((aLine=br.readLine())!=null){
                System.out.println(aLine);
                builder.append(aLine);
            }
            LogManager.getLogger().info("[End ofResponse]\n");
            
            latLong = GoogleMapsAddressResponseDTOAdapter.getGeocodedAddress(builder.toString());
        } catch (MalformedURLException ex) {
        	LogManager.getLogger().error("Invalid URL: \n");
        } catch (IOException ioe) {
        	LogManager.getLogger().error("Couldn't reverse geo-code the address!", ioe);
        }finally{
            try{
                if(is!=null){
                    is.close();
                }
            } catch (IOException ioe){
            	LogManager.getLogger().error("Couldn't reverse geo-code the address!", ioe);
            }
        }
        
    	return latLong;
    }
}
