/**
 * parshwabhoomi-server	12-Nov-2017:3:31:41 PM
 */
package org.cs.parshwabhoomi.server.domainobjects;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public abstract class DBEntity {
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
