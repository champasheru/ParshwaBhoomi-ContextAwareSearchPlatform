/**
 * parshwabhoomi-server	12-Nov-2017:7:09:40 PM
 */
package org.cs.parshwabhoomi.server.domainobjects;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class EndUserPref extends DBEntity {
	private String userId;
	private BusinessCategory businessCategory;
	private String prefValue;
	
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return the businessCategory
	 */
	public BusinessCategory getBusinessCategory() {
		return businessCategory;
	}
	/**
	 * @param businessCategory the businessCategory to set
	 */
	public void setBusinessCategory(BusinessCategory businessCategory) {
		this.businessCategory = businessCategory;
	}
	/**
	 * @return the prefValue
	 */
	public String getPrefValue() {
		return prefValue;
	}
	/**
	 * @param prefValue the prefValue to set
	 */
	public void setPrefValue(String prefValue) {
		this.prefValue = prefValue;
	}
	
}
