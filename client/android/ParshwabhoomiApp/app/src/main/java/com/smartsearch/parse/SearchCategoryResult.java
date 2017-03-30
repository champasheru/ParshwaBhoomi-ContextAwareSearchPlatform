package com.smartsearch.parse;

public class SearchCategoryResult extends SearchResult {

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setTitle(String title){
		this.title = title;
	}

}
