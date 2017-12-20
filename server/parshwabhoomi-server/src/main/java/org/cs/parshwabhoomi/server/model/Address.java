/**
 * parshwabhoomi-server	12-Nov-2017:3:18:50 PM
 */
package org.cs.parshwabhoomi.server.model;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class Address {
	private String id;
	private String premise;
	private String routeOrLane;
    private String sublocality;
    private String locality;
    private String pincode;
    private String state;
    private String latitude;
    private String longitude;
    
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the premise
	 */
	public String getPremise() {
		return premise;
	}
	/**
	 * @param premise the premise to set
	 */
	public void setPremise(String premise) {
		this.premise = premise;
	}
	/**
	 * @return the routeOrLane
	 */
	public String getRouteOrLane() {
		return routeOrLane;
	}
	/**
	 * @param routeOrLane the routeOrLane to set
	 */
	public void setRouteOrLane(String routeOrLane) {
		this.routeOrLane = routeOrLane;
	}
	/**
	 * @return the sublocality
	 */
	public String getSublocality() {
		return sublocality;
	}
	/**
	 * @param sublocality the sublocality to set
	 */
	public void setSublocality(String sublocality) {
		this.sublocality = sublocality;
	}
	/**
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}
	/**
	 * @param locality the locality to set
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}
	/**
	 * @return the pincode
	 */
	public String getPincode() {
		return pincode;
	}
	/**
	 * @param pincode the pincode to set
	 */
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * 
	 * @return human readable formatted address.
	 */
	public String getFormattedAddress() {
		return routeOrLane+","+sublocality+","+locality+","+state+","+pincode;
	}
}
