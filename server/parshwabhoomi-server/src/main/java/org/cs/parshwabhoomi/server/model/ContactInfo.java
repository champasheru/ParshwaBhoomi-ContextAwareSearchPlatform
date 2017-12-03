/**
 * parshwabhoomi-server	12-Nov-2017:3:20:43 PM
 */
package org.cs.parshwabhoomi.server.model;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class ContactInfo {
	private String primaryMobile;
	private String secondaryMobile;
	private String landline;
	private String email;
	//can add open from to; working hours between etc.
	
	/**
	 * @return the primaryMobile
	 */
	public String getPrimaryMobile() {
		return primaryMobile;
	}
	/**
	 * @param primaryMobile the primaryMobile to set
	 */
	public void setPrimaryMobile(String primaryMobile) {
		this.primaryMobile = primaryMobile;
	}
	/**
	 * @return the secondaryMobile
	 */
	public String getSecondaryMobile() {
		return secondaryMobile;
	}
	/**
	 * @param secondaryMobile the secondaryMobile to set
	 */
	public void setSecondaryMobile(String secondaryMobile) {
		this.secondaryMobile = secondaryMobile;
	}
	/**
	 * @return the landline
	 */
	public String getLandline() {
		return landline;
	}
	/**
	 * @param landline the landline to set
	 */
	public void setLandline(String landline) {
		this.landline = landline;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
