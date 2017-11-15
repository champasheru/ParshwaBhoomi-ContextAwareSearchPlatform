/**
 * parshwabhoomi-server	10-Nov-2017:1:03:42 PM
 */
package org.cs.parshwabhoomi.server.restclient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public abstract class AbstractRESTRequest implements RESTRequest {
	private String url;
	private Method method;
	private ContentType contenType;
	
	protected Map<String, String> headers = new HashMap<>();
	
	private byte[] payload;

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#setUrl(java.lang.String)
	 */
	@Override
	public void setUrl(String url) {
		this.url =  url;
		
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#setMethod(org.cs.parshwabhoomi.server.restclient.RESTRequest.Method)
	 */
	@Override
	public void setMethod(Method method) {
		this.method = method;
		
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#setContentType(org.cs.parshwabhoomi.server.restclient.RESTRequest.ContentType)
	 */
	@Override
	public void setContentType(ContentType contentType) {
		this.contenType = contentType;
		
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#setHeader(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setHeader(String key, String value) {
		headers.put(key, value);
		
	}
	
	
	public String getHeader(String key){
		return headers.get(key);
	}


	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the method
	 */
	public RESTRequest.Method getMethod() {
		if(method == null){
			return Method.GET;
		}
		return method;
	}

	/**
	 * @return the contentType
	 */
	public ContentType getContentType() {
		return contenType;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#getHeaders()
	 */
	@Override
	public Map<String, String> getHeaders() {
		return headers;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#setPayload(byte[])
	 */
	@Override
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTRequest#getPayload()
	 */
	@Override
	public byte[] getPayload() {
		return payload;
	}
	
}
