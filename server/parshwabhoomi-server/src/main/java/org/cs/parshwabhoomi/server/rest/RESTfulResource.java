package org.cs.parshwabhoomi.server.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 * 
 * @author saurabh
 * git: Saurabh Sirdeshmukh saurabh@geotrackers.com
 *
 * The super interface for all the RESTful resources.
 * Specific resources may define their functionality specific sub-interfaces and provide their concrete implementations.
 * 
 * Refer: 
 * http://restful-api-design.readthedocs.io/en/latest/methods.html
 * https://spring.io/understanding/REST
 * http://restcookbook.com/HTTP%20Methods/put-vs-post/
 */
public interface RESTfulResource {
	public static final String DEFAULT_GET_PATH = "/{resourceId}";
	public static final String DEFAULT_GETALL_PATH = "/";
	public static final String DEFAULT_FIND_PATH = "/find";
	public static final String DEFAULT_DELETE_PATH = "/{resourceId}";
		
	@GET
	@Path(DEFAULT_GETALL_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll();
	
	@GET
	@Path(DEFAULT_GET_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("resourceId") String resourceId);
	
	@DELETE
	@Path(DEFAULT_DELETE_PATH)
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("resourceId") String resourceId);
}
