/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.domainobjects;

/**
 *
 * @author saurabh
 * DB table: categories
 */
public enum BusinessCategory{
	TYPE_COMPUTER_ELECTRONICS_GADGETS(1, "Computers,Electronics,Gadgets"),
	TYPE_AUTOMOBILES(2, "Automobiles,Cars"),
	TYPE_FOOD(3, "Food"),
	TYPE_LIFESTYLE(4, "Lifestyle"),
	TYPE_TRAVEL_LEISURE(5, "Travel,Leisure"),
	TYPE_EDUCATION_ACADEMICS_TRAINING(6, "Education,Academics,Training");
	
	private long id;
	private String description;
	
	private BusinessCategory(long id, String description){
		this.description = description;
	}
	

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
