/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.domainobjects;

/**
 *
 * @author saurabh
 */
public class UserCredential extends DBEntity{
	public enum Role{
		END_USER,
		BUSINESS_ENTITY
	}
	
    private String username;
    private String password;
    //EndUser or BusinessEntity
    private Role role;
    

    public UserCredential(){
        
    }

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

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}
}
