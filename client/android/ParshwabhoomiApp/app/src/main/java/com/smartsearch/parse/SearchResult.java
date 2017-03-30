package com.smartsearch.parse;

public abstract class SearchResult
{
	protected String type;
	protected String title;
	
	public abstract String getTitle();
	public abstract String getType();
}
