package org.cs.parshwabhoomiapp.data;

import android.content.Context;

import org.cs.parshwabhoomiapp.parse.SmartSearchEngine;
import org.cs.parshwabhoomiapp.util.SmartSearchLocationListener;

public class Config 
{
	public static SmartSearchEngine searchEngine = new SmartSearchEngine();;
	public static String lat;
	public static String lon;
	private static String typeGeneral = "RESULT_GENERAL";
	private static String typeLocation = "RESULT_LOCATION";
	private static String typeUpLocation = "RESULT_USERPREF_LOCATION";
	private static String typeVendor = "RESULT_VENDOR";
	private static String typeCategory = "RESULT_CATEGORY";
	

	private static String typeHeader = "title";
	private static String resultDialogTitile = "Custom Result Information";
	private static Context context;
	private static SmartSearchLocationListener listener ;

	public static SmartSearchLocationListener getListener() {
		return listener;
	}

	public static void setListener(SmartSearchLocationListener listener) {
		Config.listener = listener;
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		Config.context = context;
	}
	
	public static String getTypeCategory() {
		return typeCategory;
	}

	public static String getTypeHeader() 
	{
		return typeHeader;
	}

	public static String getResultDialogTitile() 
	{
		return resultDialogTitile;
	}

	public static String getTypeGeneral() 
	{
		return typeGeneral;
	}

	public static String getTypeLocation() 
	{
		return typeLocation;
	}

	public static String getTypeUpLocation() 
	{
		return typeUpLocation;
	}

	public static String getTypeVendor() 
	{
		return typeVendor;
	}

	public static String getLat() 
	{
		return lat;
	}

	public static void setLat(String lat) 
	{
		Config.lat = lat;
	}

	public static String getLon() 
	{
		return lon;
	}

	public static void setLon(String lon) 
	{
		Config.lon = lon;
	}

	public static SmartSearchEngine getSearchEngine() 
	{
		return searchEngine;
	}
	
}
