/**
 * parshwabhoomi-server	03-Dec-2017:2:47:51 PM
 */
package org.cs.parshwabhoomi.server.model;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 * Provides the search context which encapsulates different search criterias and acts
 * as an inout to the further context aware search processing. 
 */
public class SearchContext {
	private float latitude;
	private float longitude;
	private String query;
	UserCredential userCredential;
	
	
	public SearchContext(){
		
	}
	
	/**
	 * @param latitude
	 * @param longitude
	 * @param query
	 * @param userCredential
	 */
	public SearchContext(float latitude, float longitude, String query, UserCredential userCredential) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.query = query;
		this.userCredential = userCredential;
	}
	/**
	 * @return the latitude
	 */
	public float getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public float getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the query
	 */
	public String getQuery() {
		return query;
	}
	/**
	 * @param query the query to set
	 */
	public void setQuery(String query) {
		this.query = query;
	}
	/**
	 * @return the userCredential
	 */
	public UserCredential getUserCredential() {
		return userCredential;
	}
	/**
	 * @param userCredential the userCredential to set
	 */
	public void setUserCredential(UserCredential userCredential) {
		this.userCredential = userCredential;
	}
}	
