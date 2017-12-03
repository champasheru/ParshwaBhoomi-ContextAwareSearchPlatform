/**
 * parshwabhoomi-server	01-Dec-2017:10:13:26 PM
 */
package org.cs.parshwabhoomi.server.dao.raw;

import org.cs.parshwabhoomi.server.model.EndUser;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public interface EndUserDao {
	void addUserProfile(EndUser endUser);
	
	void updateUserProfile(EndUser endUser);
	
	EndUser getEndUserDetailedProfile(String username);
}
