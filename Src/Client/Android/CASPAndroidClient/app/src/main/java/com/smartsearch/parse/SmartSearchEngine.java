package com.smartsearch.parse;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.smartsearch.data.Config;

import android.util.Log;

public class SmartSearchEngine
{
	private SAXParserFactory spf;
	private SAXParser sp;
	private SmartSearchXmlHandler myXMLHandler  = new SmartSearchXmlHandler();	;
	private ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>() ;
	private ArrayList<SearchServiceResult> upLocationResults;
	private ArrayList<SearchServiceResult> locationResults;
	private ArrayList<SearchServiceResult> generalResults;
	private ArrayList<CustomSearchResult> vendorResults;
	
	//Server URL
	private String url;
	//to access the localhost from emulator
	//private String url = "http://10.0.2.2:8080/SmartSearchServer/";
	
	private LoginResult loginResult ;
	
	
	public ArrayList<SearchResult> getSearchResults() 
	{
		return searchResults;
	}


	public void parseSearchResults(String query,String username, String lat, String lon)throws SocketException,SAXException,IOException, ParserConfigurationException 
	{
		/** Handling XML */
		spf = SAXParserFactory.newInstance();
	
		sp  = spf.newSAXParser();
		
		/** Send URL to parse XML Tags */
		URL sourceUrl;
		if(lat == null || lon == null){
			sourceUrl = new URL(url+"Search?query="+URLEncoder.encode(query)+"&username="+username+"&lat=17.674553&lng=75.323723");//Pandharpur lat long default
		}else{
			sourceUrl = new URL(url+"Search?query="+URLEncoder.encode(query)+"&username="+username+"&lat="+lat+"&lng="+lon);
		}
	
		System.out.println("URL = "+sourceUrl);
		
		sp.parse(new InputSource(sourceUrl.openStream()),myXMLHandler);
		
		vendorResults = myXMLHandler.getCustomsearchResults();
		SearchCategoryResult category = new SearchCategoryResult();
		category.setType(Config.getTypeCategory());
		category.setTitle("Results For Local Vendors ("+vendorResults.size()+")");
		searchResults.add(category);
		searchResults.addAll(vendorResults);
		
		upLocationResults = myXMLHandler.getUpLocationResults();
		category = new SearchCategoryResult();
		category.setType(Config.getTypeCategory());
		category.setTitle("Results For User Preferences & Location ("+upLocationResults.size()+")");
		searchResults.add(category);
		searchResults.addAll(upLocationResults);
		
		locationResults = myXMLHandler.getLocationResults();
		category = new SearchCategoryResult();
		category.setType(Config.getTypeCategory());
		category.setTitle("Results For Location ("+locationResults.size()+")");
		searchResults.add(category);
		searchResults.addAll(locationResults);
		
		generalResults = myXMLHandler.getGeneralResults();
		category = new SearchCategoryResult();
		category.setType(Config.getTypeCategory());
		category.setTitle("Results For General ("+generalResults.size()+")");
		searchResults.add(category);
		searchResults.addAll(generalResults);
		
		System.out.println("Size of Master search result is = "+searchResults.size());
	}
	
	public void parseLoginResult(String username, String password, String serverURL) throws IOException,ParserConfigurationException,SAXException
	{
			HttpClient httpClient = new DefaultHttpClient();
			if(serverURL.equals("")){
				Log.i("Login", "Using default server URL");
				url = "http://123.236.189.37:8080/SmartSearchServer/";
			}else if(serverURL.startsWith("http") || serverURL.startsWith("https")){
				url = serverURL+"/SmartSearchServer/";
			}else{
				url = "http://"+serverURL+"/SmartSearchServer/";
			}
			
			Log.i("Login", "Server URL = "+url);
		
			HttpPost  httpPost = new HttpPost(url+"/Login");
			
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
			
			nameValuePairs.add(new BasicNameValuePair("username", username.trim()));
			
			nameValuePairs.add(new BasicNameValuePair("password", password.trim()));
			
			nameValuePairs.add(new BasicNameValuePair("from", "device"));
			
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			
			HttpResponse httpResponse = httpClient.execute(httpPost);
			
			httpResponse.getEntity();
			
			spf = SAXParserFactory.newInstance();
			
			sp = spf.newSAXParser();
			
			InputStream in =httpResponse.getEntity().getContent();
			
			sp.parse(in,myXMLHandler);
			
			setLoginResult(myXMLHandler.getLoginResult());
			
	}
	
	
	public LoginResult getLoginResult() 
	{
		return loginResult;
	}


	public void setLoginResult(LoginResult loginResult) 
	{
		this.loginResult = loginResult;
	}

}
