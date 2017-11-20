/**
 * parshwabhoomi-server	12-Nov-2017:3:34:05 PM
 */
package org.cs.parshwabhoomi.server.domainobjects;

import java.util.Date;
import java.util.EnumMap;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 * DB tables: end_users, user_preferences
 *
 */
public class EndUser extends PersistentEntity {
	private UserCredential userCredential; 
	private String name;
	private Date dateOfBirth;
    private Address address;
    private ContactInfo contactInfo;
    private String educationInfo;
    private String workInfo;
    //A map from BusinessCategoryEnum => Preferences String for this user.
    private EnumMap<BusinessCategory,String> userPrefs;
    
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
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
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
	 * @return the educationInfo
	 */
	public String getEducationInfo() {
		return educationInfo;
	}

	/**
	 * @param educationInfo the educationInfo to set
	 */
	public void setEducationInfo(String educationInfo) {
		this.educationInfo = educationInfo;
	}

	/**
	 * @return the workInfo
	 */
	public String getWorkInfo() {
		return workInfo;
	}

	/**
	 * @param workInfo the workInfo to set
	 */
	public void setWorkInfo(String workInfo) {
		this.workInfo = workInfo;
	}

	/**
	 * @return the userPrefs
	 */
	public EnumMap<BusinessCategory, String> getUserPrefs() {
		return userPrefs;
	}

	/**
	 * @param userPrefs the userPrefs to set
	 */
	public void setUserPrefs(EnumMap<BusinessCategory, String> userPrefs) {
		this.userPrefs = userPrefs;
	}

}
