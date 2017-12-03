/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.cs.parshwabhoomi.server.model;

/**
 *
 * @author saurabh
 * DB table: categories
 */
public enum BusinessCategory{
	COMPUTERS(1, "Computers/Electronics/Gadgets"),
	AUTOMOBILES(2, "Cars/Vehicles/Automobiles"),
	FOOD(3, "Favorite Food/Cuisines"),
	LIFESTYLE(4, "Lifestyle/Shopping"),
	TRAVEL(5, "Travel/Leisure"),
	TRAINING(6, "Education/Academics/Training/Courses");
	
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
