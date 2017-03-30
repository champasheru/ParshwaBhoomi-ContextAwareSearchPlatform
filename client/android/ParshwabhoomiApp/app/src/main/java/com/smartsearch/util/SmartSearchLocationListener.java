package com.smartsearch.util;

import com.smartsearch.data.Config;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class SmartSearchLocationListener 
{
	private static String latitude;
	private static String longitude;
	private static LocationListener locationListener;
	
	
	
		
	public  void registerLocationListener()
	{
    	// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) Config.getContext().getSystemService(Context.LOCATION_SERVICE);
		// Define a listener that responds to location updates
		locationListener = new LocationListener() {
		   
		   
			public void onLocationChanged(Location location) 
			{
			 	if(location!=null)
		    	{
		    		Config.setLat(""+location.getLatitude());
		    		Config.setLon(""+location.getLongitude());
		    		System.out.println("Lat = "+Config.getLat()+"  Lon = "+Config.getLon());
		       	}
		   
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
		  };
		// Register the listener with the Location Manager to receive location updates
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,locationListener); 
			  
	}


}
