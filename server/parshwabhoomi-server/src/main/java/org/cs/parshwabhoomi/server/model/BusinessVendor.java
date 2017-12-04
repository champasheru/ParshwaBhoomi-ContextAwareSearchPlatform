/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.model;

/**
 *
 * @author gayatri
 * DB table: business_vendors
 */
public class BusinessVendor extends PersistentEntity{
	private UserCredential userCredential;
    private String name;
    private BusinessCategory businessCategory;
    //Generally a one liner that captures the business entity's core business/service.
    private String tagLine;
    private String offerings;
    private Address address;
    private ContactInfo contactInfo;
    

    public BusinessVendor(){
        
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


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the tagLine
	 */
	public String getTagLine() {
		return tagLine;
	}


	/**
	 * @param tagLine the tagLine to set
	 */
	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}


	/**
	 * @return the offerings
	 */
	public String getOfferings() {
		return offerings;
	}


	/**
	 * @param offerings the offerings to set
	 */
	public void setOfferings(String offerings) {
		this.offerings = offerings;
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
}
