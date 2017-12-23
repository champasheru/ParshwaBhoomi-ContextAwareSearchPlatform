/**
 * parshwabhoomi-server	10-Nov-2017:2:18:51 PM
 */
package org.cs.parshwabhoomiapp.client.framework.restful;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public abstract class AbstractRESTClient implements RESTClient {
	protected Map<String, String> headers = new HashMap<>();
	protected boolean trustSelfSignedCertificates = false;

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#setHeader(java.lang.String, java.lang.String)
	 */
	@Override
	public void setHeader(String key, String value) {
		headers.put(key, value);

	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#setTrustSelfSignedCertificates(boolean)
	 */
	@Override
	public void setTrustSelfSignedCertificates(boolean flag) {
		trustSelfSignedCertificates = flag;
	}
}
