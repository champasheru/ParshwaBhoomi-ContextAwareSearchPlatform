/**
 * parshwabhoomi-server	10-Nov-2017:1:16:15 PM
 */
package org.cs.parshwabhoomiapp.client.framework.restful;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public interface RESTClient {
	/**
	 * 
	 * @param key
	 * @param value
	 * These are client level default request headers i.e once set here these headers are applied to each and every request executed through this client.
	 * These headers can be overriden per request by setting the specific headers in an individual RESTRequest. 
	 */
	void setHeader(String key, String value);
	/**
	 * 
	 * @param flag false by default.
	 */
	void setTrustSelfSignedCertificates(boolean flag);
	
	void connect(RESTRequest restRequest) throws IOException;
	
	int getStatusCode() throws IOException;
	
	RESTRequest.ContentType getContentType();
	
	InputStream getInputStream() throws IOException;
	
	OutputStream getOutputStream() throws IOException;
	
	byte[] getResponsePayload() throws IOException;
	
	void close() throws IOException;
}
