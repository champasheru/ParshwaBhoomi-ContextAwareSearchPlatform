/**
 * parshwabhoomi-server	29-Oct-2017:7:47:00 PM
 * saurabh
 * git: Saurabh Sirdeshmukh saurabh@geotrackers.com
 */
package org.cs.parshwabhoomi.server.rest.impl;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.raw.impl.UserDAOImpl;
import org.cs.parshwabhoomi.server.dto.impl.UserLoginRequestDTO;
import org.cs.parshwabhoomi.server.rest.AbstractResource;
import org.cs.parshwabhoomi.server.rest.UserResource;

/**
 * @author saurabh
 * git: Saurabh Sirdeshmukh saurabh@geotrackers.com
 *
 */
@Path("/user")
public class UserResourceImpl extends AbstractResource implements UserResource {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.rest.UserResource#login(org.cs.parshwabhoomi.server.dto.impl.UserLoginRequestDTO)
	 */
	@Override
	public Response login(UserLoginRequestDTO loginRequestDTO) {
		LogManager.getLogger().info("Validating User Login: "+loginRequestDTO.getUsername());
		UserDAOImpl userDAOImpl = (UserDAOImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("UserDAOImpl");
		boolean isValid = userDAOImpl.isValidUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		userDAOImpl.close();
		Response response = isValid ? Response.ok().build() : Response.status(Status.UNAUTHORIZED).build();
		return response;
	}
}
