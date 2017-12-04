/**
 * parshwabhoomi-server	03-Dec-2017:3:19:58 PM
 */
package org.cs.parshwabhoomi.server.dto.adapter;

import org.cs.parshwabhoomi.server.dto.search.SearchRequestDTO;
import org.cs.parshwabhoomi.server.model.SearchContext;
import org.cs.parshwabhoomi.server.model.UserCredential;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class SearchRquestDTOAdapter {
	public SearchContext buildRequest(SearchRequestDTO dto){
		SearchContext context = new SearchContext();
		context.setLatitude(dto.getLatitude());
		context.setLongitude(dto.getLongitude());
		context.setQuery(dto.getQuery());
		
		UserCredential credential = new UserCredential();
		//TODO: parse Authorization HTTP header for basic Auth and populate credential.username
		credential.setUsername(dto.getUsername().trim());
		context.setUserCredential(credential);
		return context;
	}
}
