package org.cs.parshwabhoomiapp.util;

import org.cs.parshwabhoomiapp.data.Config;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class SmartSearchLocationListener {
	private static String latitude;
	private static String longitude;
	private static LocationListener locationListener;

	public void registerLocationListener() {
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) Config.getContext().getSystemService(Context.LOCATION_SERVICE);
		// Define a listener that responds to location updates
		locationListener = new LocationListener() {


			public void onLocationChanged(Location location) {
				if (location != null) {
					Config.setLat("" + location.getLatitude());
					Config.setLon("" + location.getLongitude());
					System.out.println("Lat = " + Config.getLat() + "  Lon = " + Config.getLon());
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

		if (ActivityCompat.checkSelfPermission(Config.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Config.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			  
	}


}
