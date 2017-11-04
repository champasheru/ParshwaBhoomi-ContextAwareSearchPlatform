/**
 * parshwabhoomi-server	28-Oct-2017:7:01:45 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cs.parshwabhoomi.server.dto.impl.UserLoginRequestDTO;

/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 *
 */
public interface UserResource {
	public static final String LOGIN_REQUEST_URI = "/login";
	
	@Path(LOGIN_REQUEST_URI)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(UserLoginRequestDTO loginRequestDTO);
}
