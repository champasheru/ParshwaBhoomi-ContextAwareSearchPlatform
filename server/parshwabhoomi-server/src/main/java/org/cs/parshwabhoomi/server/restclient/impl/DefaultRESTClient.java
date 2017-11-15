/**
 * parshwabhoomi-server	10-Nov-2017:2:28:32 PM
 */
package org.cs.parshwabhoomi.server.restclient.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.restclient.AbstractRESTClient;
import org.cs.parshwabhoomi.server.restclient.RESTRequest;
import org.cs.parshwabhoomi.server.restclient.RESTRequest.ContentType;
import org.cs.parshwabhoomi.server.restclient.RESTRequest.Method;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class DefaultRESTClient extends AbstractRESTClient {
	private HttpURLConnection connection;
	private boolean isConnected = false;
	private boolean outputStreamInUse = false;
	private boolean inputStreamInUse = false;
	private RESTRequest currentRequest;
	
	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#connect(org.cs.parshwabhoomi.server.restclient.RESTRequest)
	 */
	@Override
	public void connect(RESTRequest restRequest) throws IOException {
		LogManager.getLogger().info("Executing request: "+restRequest.getUrl());
		LogManager.getLogger().info("Request method: "+restRequest.getMethod());
		LogManager.getLogger().info("Request content type: "+restRequest.getContentType());
		
		for(Iterator<String> iterator = headers.keySet().iterator(); iterator.hasNext();){
			String key = iterator.next();
			if(!restRequest.getHeaders().containsKey(key)){
				restRequest.setHeader(key, headers.get(key));
			}	
		}
		
		URL url = new URL(restRequest.getUrl());
		connection = (HttpURLConnection)url.openConnection();
		for(Iterator<String> iterator = restRequest.getHeaders().keySet().iterator(); iterator.hasNext();){
			String key = iterator.next();
			if(restRequest.getHeaders().get(key) != null){
				connection.setRequestProperty(key, restRequest.getHeaders().get(key));
			}
		}
		
		connection.setRequestMethod(restRequest.getMethod().name());
		if(restRequest.getContentType() != null){
			connection.setRequestProperty("Content-Type", restRequest.getContentType().name());
		}
		LogManager.getLogger().info("Headers set on the connection: "+connection.getRequestProperties());
		
		connection.connect();
		
		if(shallHandleBody(restRequest.getMethod())){
			if(restRequest.getPayload() != null){
				outputStreamInUse = true;
				connection.setDoOutput(true);
				connection.getOutputStream().write(restRequest.getPayload());
			}
		}
		
		isConnected = true;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#getStatusCode()
	 */
	@Override
	public int getStatusCode() throws IOException {
		if(!isConnected){
			throw new IOException("URL connection yet to be established!");
		}
		
		LogManager.getLogger().info("Response status code: "+connection.getResponseCode());
		return connection.getResponseCode();
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#getContentType()
	 */
	@Override
	public ContentType getContentType() {
		assert(isConnected == true);
		return ContentType.valueOf(connection.getContentType());
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#getInputStream()
	 */
	@Override
	public InputStream getInputStream() throws IOException {
		if(isConnected && !inputStreamInUse){
			return connection.getInputStream();
		}
		throw new IOException("Couldn't open input stream: input stream already marked for use or connection is in invaid state!");
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#getOutputStream()
	 */
	@Override
	public OutputStream getOutputStream() throws IOException {
		if(shallHandleBody(currentRequest.getMethod()) && isConnected && !outputStreamInUse){
			connection.setDoOutput(true);
			return connection.getOutputStream();
		}
		throw new IOException("Couldn't open output stream: invalid request type or output stream already marked for use or connection is in invaid state!");
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#close()
	 */
	@Override
	public void close() throws IOException {
		if(!isConnected){
			throw new IOException("URL connection yet to be established!");
		}
		connection.disconnect();
	}
	
	
	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.restclient.RESTClient#getResponsePayload()
	 */
	@Override
	public byte[] getResponsePayload() throws IOException{
		InputStream is = getInputStream();
		
		byte[] 	responsePayload = null;
		int contentLength = connection.getContentLength();
		LogManager.getLogger().info("Response content length : "+contentLength);
		
		if(contentLength != -1){
			responsePayload = new byte[contentLength];
			int offset = 0;
			int numBytesRead = 0;
			int remainingContentLength = contentLength;
			while(offset < contentLength && (numBytesRead = is.read(responsePayload, offset, remainingContentLength)) != -1){
				offset += numBytesRead;
				remainingContentLength -= offset;
			}
			is.close();
			LogManager.getLogger().info("Response payload read: "+offset);
			LogManager.getLogger().info("Response payload received: "+new String(responsePayload, "UTF-8"));
		}
		
		return responsePayload;
	}

	private boolean shallHandleBody(RESTRequest.Method method){
		return (method == Method.POST || method == Method.PUT);
	}

}
