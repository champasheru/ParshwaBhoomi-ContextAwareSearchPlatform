package org.cs.parshwabhoomiapp.parse;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import org.cs.parshwabhoomiapp.data.Config;

public class SmartSearchXmlHandler extends DefaultHandler 
{
	
	private SearchServiceResult searchServiceResult;
	private CustomSearchResult customResult;
	private LoginResult loginResult;
	private String temp;
	private String type;
	
	private ArrayList<SearchServiceResult> upLocationResults ;
	private ArrayList<SearchServiceResult> generalResults ;
	private ArrayList<SearchServiceResult> locationResults ;
	private ArrayList<CustomSearchResult> customsearchResults;
	
	public LoginResult getLoginResult() 
	{
		return loginResult;
	}
		
	public ArrayList<SearchServiceResult> getUpLocationResults() 
	{
		return upLocationResults;
	}

	public void setUpLocationResults(ArrayList<SearchServiceResult> upLocationResults) 
	{
		this.upLocationResults = upLocationResults;
	}

	public ArrayList<SearchServiceResult> getGeneralResults() 
	{
		return generalResults;
	}

	public void setGeneralResults(ArrayList<SearchServiceResult> generalResults) 
	{
		this.generalResults = generalResults;
	}

	public ArrayList<SearchServiceResult> getLocationResults() 
	{
		return locationResults;
	}

	public void setLocationResults(ArrayList<SearchServiceResult> locationResults) 
	{
		this.locationResults = locationResults;
	}

	public ArrayList<CustomSearchResult> getCustomsearchResults() 
	{
		return customsearchResults;
	}

	public void setCustomsearchResults(ArrayList<CustomSearchResult> customsearchResults) 
	{
		this.customsearchResults = customsearchResults;
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException 
	{
		if(localName.equalsIgnoreCase("resultset"))
		{
			System.out.println("ResultSet found");
			upLocationResults = new ArrayList<SearchServiceResult>();
			locationResults =  new ArrayList<SearchServiceResult>();
			generalResults =  new ArrayList<SearchServiceResult>();
			customsearchResults = new ArrayList<CustomSearchResult>();
		}
		
		else if (localName.equalsIgnoreCase("result"))
		{
			/** Start */
			System.out.println("<Result> found\n====");
			searchServiceResult = new SearchServiceResult();
		}
		else if(localName.equalsIgnoreCase("customresult"))
		{
			System.out.println("<CustomResult> found\n====");
			customResult = new CustomSearchResult();
		}
		else if(localName.equalsIgnoreCase("loginresult"))
		{
			System.out.println("<LoginResult> found");
			loginResult = new LoginResult();
		}
		
		temp = "";
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)	throws SAXException 
	{
			if(localName.equalsIgnoreCase("resultset")){
				System.out.println("Results Found for Vendor = "+customsearchResults.size());
				System.out.println("Results Found for User Pref Location = "+upLocationResults.size());
				System.out.println("Results Found for Location = "+locationResults.size());
				System.out.println("Results Found for General = "+generalResults.size());
			}else if(localName.equalsIgnoreCase("result"))
			{
				if(type.equalsIgnoreCase(Config.getTypeLocation()))
				{	
					Log.i("SSXMLHandler", "====Loc Result End");
					locationResults.add(searchServiceResult);
				}
				else if(type.equalsIgnoreCase(Config.getTypeGeneral()))
				{
					Log.i("SSXMLHandler", "====Gen Result End");
					generalResults.add(searchServiceResult);
				}
				else if(type.equalsIgnoreCase(Config.getTypeUpLocation()))
				{
					Log.i("SSXMLHandler", "====Loc Result End");
					upLocationResults.add(searchServiceResult);
				}
			}
			else if(localName.equalsIgnoreCase("customresult"))
			{
				Log.i("SSXMLHandler", "====Custom Result End");
				customsearchResults.add(customResult);
			}
			else if(localName.equalsIgnoreCase("type"))
			{
				type = temp.trim();
				if(type.equalsIgnoreCase(Config.getTypeVendor()))
					customResult.setType(type.trim());
				else
					searchServiceResult.setType(type.trim());
			}
			else if(localName.equalsIgnoreCase("title"))
			{
				searchServiceResult.setTitle(temp.trim());
				System.out.println("Title = "+temp);
			}
			else if(localName.equalsIgnoreCase("summary"))
			{
				searchServiceResult.setSummary(temp.trim());
				System.out.println("Summary = "+temp);
			}
			else if(localName.equalsIgnoreCase("url"))
			{
				searchServiceResult.setUrl(temp.trim());
				System.out.println("URL = "+temp);
			}
			else if(localName.equalsIgnoreCase("name"))
			{
				customResult.setTitle(temp.trim());
				System.out.println("Name = "+temp);
			}
			else if(localName.equalsIgnoreCase("category"))
			{
				customResult.setCategory(temp.trim());
				System.out.println("Category = "+temp);
			}
			else if(localName.equalsIgnoreCase("address"))
			{
				customResult.setAddress(temp.trim());
				System.out.println("Address = "+temp);
			}
			else if(localName.equalsIgnoreCase("contact"))
			{
				customResult.setContact(temp.trim());
				System.out.println("Contact = "+temp);
			}
			else if(localName.equalsIgnoreCase("offerings"))
			{
				System.out.println("Offerings = "+temp);
				customResult.setOfferings(temp.trim());
			}
			else if(localName.equalsIgnoreCase("status"))
			{
				loginResult.setStatus(temp.trim());
				System.out.println("Status = "+temp);
			}
			else if(localName.equalsIgnoreCase("error"))
			{
				loginResult.setErrorText(temp.trim());
				System.out.println("Error = "+temp);
			}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)throws SAXException 
	{
		temp += new String(ch, start, length);
	}
}
