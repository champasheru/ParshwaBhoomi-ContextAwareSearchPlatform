/**
 * parshwabhoomi-server	21-Dec-2017:3:43:40 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.model.BusinessVendor;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class GeofenceBasedVendorSelector {
	private float lat;
	private float lng;
	//in KM
	private float radius;
	public static final float DEFAULT_RADIUS_IN_METERS = 5*1000;
	
	
	/**
	 * @param lat
	 * @param lng
	 * @param radius
	 */
	public GeofenceBasedVendorSelector(float lat, float lng, float radius) {
		this.lat = lat;
		this.lng = lng;
		this.radius = radius;
	}
	
	
	/**
	 * @param lat
	 * @param lng
	 */
	public GeofenceBasedVendorSelector(float lat, float lng) {
		this(lat, lng, DEFAULT_RADIUS_IN_METERS);
	}


	public List<BusinessVendor> findWithinRadius(List<BusinessVendor> list){
		LogManager.getLogger().info("Finding all the vendors within radius of:"+radius+" from:"+lat+", "+lng);
		
		List<BusinessVendor> vendors = new ArrayList<>();
		for(BusinessVendor vendor : list){
			if(isInRadius(
					Float.parseFloat(vendor.getAddress().getLatitude()), 
					Float.parseFloat(vendor.getAddress().getLongitude()))){
				vendors.add(vendor);
			}
		}
		return vendors;
	}
	
	
	private boolean isInRadius(float vlat, float vlng){
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
