/**
 * parshwabhoomi-server	29-Oct-2017:7:39:52 PM
 * saurabh
 * git: Saurabh Sirdeshmukh saurabh@geotrackers.com
 */
package org.cs.parshwabhoomi.server.dto.impl;

import org.cs.parshwabhoomi.server.dto.AbstractRequestDTO;

/**
 * @author saurabh
 * git: Saurabh Sirdeshmukh saurabh@geotrackers.com
 *
 */
public class UserLoginRequestDTO extends AbstractRequestDTO {
	private String username;
	private String password;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
