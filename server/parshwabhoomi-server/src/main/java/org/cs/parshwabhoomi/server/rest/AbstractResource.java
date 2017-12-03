package org.cs.parshwabhoomi.server.rest;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.cs.parshwabhoomi.server.dto.AbstractRequestDTO;

public abstract class AbstractResource implements RESTfulResource {

	private @Context UriInfo uriInfo;
	private @Context HttpServletRequest request;
	private @Context SecurityContext securityContext;
	
	protected AbstractResource(){
	}


	/**
	 * @return the uriInfo
	 */
	public final UriInfo getUriInfo() {
		return uriInfo;
	}
	
	
	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}
	
	
	/**
	 * 
	 * @return the security context
	 */
	public SecurityContext getSecurityContext(){
		return securityContext;
	}


	public Response add(AbstractRequestDTO abstractRequestDTO) {
		// TODO Auto-generated method stub
		return null;
		
	}
	
	
	public Response update(AbstractRequestDTO abstractRequestDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.geotrackers.fleetmgmt.webservices.RESTfulResource#getAll()
	 */
	public Response getAll() {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see com.geotrackers.fleetmgmt.webservices.RESTfulResource#get(java.lang.String)
	 */
	public Response get(String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

	/* (non-Javadoc)
	 * @see com.geotrackers.fleetmgmt.webservices.RESTfulResource#delete(java.lang.String)
	 */
	public Response delete(String resourceId) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
