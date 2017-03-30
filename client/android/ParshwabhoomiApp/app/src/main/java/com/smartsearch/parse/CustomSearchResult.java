package com.smartsearch.parse;

public class CustomSearchResult extends SearchResult
{
	private String category="";
	private String address="";
	private String contact="";
	private String offerings="";
	
	/* Getter & Setter Name */
	
	/* Getter & Setter for Category */
	public String getCategory() 
	{
		return category;
	}
	public void setCategory(String category) 
	{
		this.category = category;
	}
	
	/* Getter & Setter for Address */
	public String getAddress() 
	{
		return address;
	}
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	/* Getter & Setter for Contacts */
	public String getContact() 
	{
		return contact;
	}
	public void setContact(String contact) 
	{
		this.contact = contact;
	}
	
	/* Getter & Setter for Offerings */
	public String getOfferings() 
	{
		return offerings;
	}
	
	public void setOfferings(String offerings) 
	{
		this.offerings = offerings;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
}
