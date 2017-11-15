/**
 * parshwabhoomi-server	12-Nov-2017:8:08:38 PM
 */
package org.cs.parshwabhoomi.server.rest.impl;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.cs.parshwabhoomi.server.rest.AbstractResource;
import org.cs.parshwabhoomi.server.rest.SearchResource;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
@Path("/usersearchv1")
public class SearchResourceImpl extends AbstractResource implements SearchResource {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.rest.SearchResource#search(java.lang.String)
	 */
	@Override
	public Response search(String queryString) {
		// TODO Auto-generated method stub
		return null;
	}

}
