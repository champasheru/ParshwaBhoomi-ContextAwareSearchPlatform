/**
 * parshwabhoomi-server	29-Oct-2017:7:16:45 PM
 * gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw;

import org.cs.parshwabhoomi.server.model.UserCredential;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public interface UserCredentialDao {
	public long addUserCredential(UserCredential userCredential);
	
	public boolean isValidUser(String username,String password);
}
