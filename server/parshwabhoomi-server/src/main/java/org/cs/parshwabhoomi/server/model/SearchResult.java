/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.model;

/**
 *
 * @author gayatri
 * This class encapsulates the complete search result information retrieved from DB for a given user
 * query by matching it against the user context info and history persisted in DB.
 * There will be a one instance of this class per search result computed by the search aggregator(and it's
 * not necessary that the result is computed from the platform DB only, it can be fetched from a remote
 * service like Google Custom Search Engine.
 * Note: this is a computed entity i.e. there's no equivalent DB table for this one.  
 */
public class SearchResult {
	public enum Provider{
		SEARCH_PROVIDER_PARSHWABHOOMI,
		SEARCH_PROVIDER_GOOGLE
	}
	
	public enum Type{
		//Result served by Parshwabhoomi platform based on user preferences and matching corresponding business entities in the given locality/geo-fence.   
		TYPE_PB_PREFERRED,
		//Result served by Parshwabhoomi platform that matches the search term against registering business entities in the given locality/geo-fence but w/o
		//the user preferences.
		TYPE_PB_LOCATION,
		//Result served by Parshwabhoomi platform that matches the search term against all the registered business entities w/o considering locality/geo-fence &
		//the user preferences.
		TYPE_PB_GENERAL,
		//Result served by Search Engine based on user preferences and matching corresponding business entities in the given locality/geo-fence.
		//The query is equivalent to one used for TYPE_PB_PREFERRED but served by search engine like Google.
		TYPE_SEARCH_ENGINE_PREFERRED,
		//Result served by Search Engine that matches the search term against business entities in the given locality/geo-fence but w/o
		//the user preferences. The query is equivalent to one used for TYPE_PB_LOCATION but served by search engine like Google.
		TYPE_SEARCH_ENGINE_LOCATION,
		//Result served by Search Engine that matches the search term against all the business entities w/o considering locality/geo-fence &
		//the user preferences. The query is equivalent to one used for TYPE_PB_GENERAL but served by search engine like Google.
		TYPE_SEARCH_ENGINE_GENERAL
	}
	
    //The result type would be one of the 6 types above.
    private Type type;
    //The provider of the search results e.g. parshwabhoomi, google etc.
    private Provider provider;
    
    private String title;
    private String htmlTitle;
    private String link;
    private String displayLink;
    private String snippet;
    private String htmlSnippet;
    private String formattedUrl;
    private String htmlFormattedUrl;
    private String imageUrl;
    /**
     * The business category/classfication name under which the local vendor falls.
     * This is applicable only when the search result type is PB: preferred, location, and general. 
     */
    
    private String category;
    
    private Address address;
    private ContactInfo contactInfo;
	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @return the provider
	 */
	public Provider getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the htmlTitle
	 */
	public String getHtmlTitle() {
		return htmlTitle;
	}
	/**
	 * @param htmlTitle the htmlTitle to set
	 */
	public void setHtmlTitle(String htmlTitle) {
		this.htmlTitle = htmlTitle;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the displayLink
	 */
	public String getDisplayLink() {
		return displayLink;
	}
	/**
	 * @param displayLink the displayLink to set
	 */
	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}
	/**
	 * @return the snippet
	 */
	public String getSnippet() {
		return snippet;
	}
	/**
	 * @param snippet the snippet to set
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	/**
	 * @return the htmlSnippet
	 */
	public String getHtmlSnippet() {
		return htmlSnippet;
	}
	/**
	 * @param htmlSnippet the htmlSnippet to set
	 */
	public void setHtmlSnippet(String htmlSnippet) {
		this.htmlSnippet = htmlSnippet;
	}
	/**
	 * @return the formattedUrl
	 */
	public String getFormattedUrl() {
		return formattedUrl;
	}
	/**
	 * @param formattedUrl the formattedUrl to set
	 */
	public void setFormattedUrl(String formattedUrl) {
		this.formattedUrl = formattedUrl;
	}
	/**
	 * @return the htmlFormattedUrl
	 */
	public String getHtmlFormattedUrl() {
		return htmlFormattedUrl;
	}
	/**
	 * @param htmlFormattedUrl the htmlFormattedUrl to set
	 */
	public void setHtmlFormattedUrl(String htmlFormattedUrl) {
		this.htmlFormattedUrl = htmlFormattedUrl;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	/**
	 * @return the contactInfo
	 */
	public ContactInfo getContactInfo() {
		return contactInfo;
	}
	/**
	 * @param contactInfo the contactInfo to set
	 */
	public void setContactInfo(ContactInfo contactInfo) {
		this.contactInfo = contactInfo;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
}
