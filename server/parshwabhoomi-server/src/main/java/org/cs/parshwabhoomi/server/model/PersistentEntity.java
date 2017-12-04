/**
 * parshwabhoomi-server	12-Nov-2017:3:31:41 PM
 */
package org.cs.parshwabhoomi.server.model;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public abstract class PersistentEntity {
	private long id;

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
}
