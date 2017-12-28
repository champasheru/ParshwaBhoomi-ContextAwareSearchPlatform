/**
 * parshwabhoomi-server	21-Dec-2017:3:43:40 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.model.SearchResult;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class Geofencer {
	private float lat;
	private float lng;
	//in KM
	private float radius;
	public static final float DEFAULT_RADIUS_IN_KM = 20;
	
	
	/**
	 * @param lat user's latitude
	 * @param lng user's longitude
	 * @param radius in Kilometers
	 */
	public Geofencer(float lat, float lng, float radius) {
		this.lat = lat;
		this.lng = lng;
		this.radius = radius;
	}
	
	
	/**
	 * @param lat user's latitude
	 * @param lng user's longitude
	 */
	public Geofencer(float lat, float lng) {
		this(lat, lng, DEFAULT_RADIUS_IN_KM);
	}

	
	/**
	 * 
	 * @param list of search result that contains local vendor's lat, long.
	 * @return all the search results that fall within the given radius from the user's lat long as a center.
	 */
	public List<SearchResult> findWithinFence(List<SearchResult> list){
		LogManager.getLogger().info("Finding all the vendors within radius of:"+radius+" from:"+lat+", "+lng);
		
		List<SearchResult> results = new ArrayList<>();
		for(SearchResult result : list){
			if(result.getAddress().getLatitude() != null && result.getAddress().getLongitude() != null){
				float vlat = Float.parseFloat(result.getAddress().getLatitude());
				float vlng = Float.parseFloat(result.getAddress().getLongitude());
				if(isInFence(vlat, vlng)){
					LogManager.getLogger().info("Vendor found within radius:"+result.getTitle());
					results.add(result);
				}
			}
		}
		LogManager.getLogger().info("Num Vendors found within radius:"+results.size());
		return results;
	}
	
	/**
	 * 
	 * @param list of search result that contains local vendor's lat, long.
	 * @return all the search results that fall outside the radius/geofence from the user's lat long as a center.
	 */
	public List<SearchResult> findOutsideFence(List<SearchResult> list){
		LogManager.getLogger().info("Finding all the vendors within radius of:"+radius+" from:"+lat+", "+lng);
		
		List<SearchResult> results = new ArrayList<>();
		for(SearchResult result : list){
			if(result.getAddress().getLatitude() == null || result.getAddress().getLongitude() == null){
				results.add(result);
			}else{
				float vlat = Float.parseFloat(result.getAddress().getLatitude());
				float vlng = Float.parseFloat(result.getAddress().getLongitude());
				if(!isInFence(vlat, vlng)){
					LogManager.getLogger().info("Vendor found outside radius:"+result.getTitle());
					results.add(result);
				}
			}
		}
		LogManager.getLogger().info("Num Vendors found within radius:"+results.size());
		return results;
	}
	
	
	private boolean isInFence(float vlat, float vlng){
		double theta = vlng - lng;
		double dist = Math.sin(deg2rad(vlat)) * Math.sin(deg2rad(lat)) + Math.cos(deg2rad(vlat)) * Math.cos(deg2rad(lat)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		//to convert to KM
		dist = dist * 1.609344;
		return (dist <= radius); 
	}
	
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
}
