/**
 * parshwabhoomi-server	29-Oct-2017:7:47:00 PM
 * saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 */
package org.cs.parshwabhoomi.server.rest.impl;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.raw.impl.UserCredentialDaoImpl;
import org.cs.parshwabhoomi.server.dto.auth.UserLoginRequestDTO;
import org.cs.parshwabhoomi.server.rest.AbstractResource;
import org.cs.parshwabhoomi.server.rest.UserResource;

/**
 * @author saurabh
 * git: champasheru Saurabh Sirdeshmukh saurabh.cse2@gmail.com
 *
 */
@Path("/user")
public class UserResourceImpl extends AbstractResource implements UserResource {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.rest.UserResource#login(org.cs.parshwabhoomi.server.dto.impl.UserLoginRequestDTO)
	 */
	@Override
	public Response login(UserLoginRequestDTO loginRequestDTO) {
		LogManager.getLogger().info("Validating UserCredential Login: "+loginRequestDTO.getUsername());
		UserCredentialDaoImpl userCredentialDaoImpl = (UserCredentialDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("UserCredentialDaoImpl");
		boolean isValid = userCredentialDaoImpl.isValidUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		userCredentialDaoImpl.close();
		Response response = isValid ? Response.ok().build() : Response.status(Status.UNAUTHORIZED).build();
		return response;
	}
}
