package org.cs.parshwabhoomi.server;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

//Singleton
public class AppContext {
	private static final String CREDS_PROP_FILE="creds.properties";
	
	public static final String GOOGLE_API_KEY="org.cs.parshwabhoomi.google.api.key";
	public static final String GOOGLE_CSE_ID="org.cs.parshwabhoomi.google.cse.id";
	
	public static final String FM_JDBC_URL="fm.jdbc.url";
	public static final String FM_JDBC_DRIVER="fm.jdbc.driver";
	public static final String FM_JDBC_USERNAME="fm.jdbc.username";
	public static final String FM_JDBC_PASSWORD="fm.jdbc.password";
	
	
	private static AppContext appContext;
	 
	private Properties props;
	
	private AppContext(){
		props = new Properties();
		try {
			LogManager.getLogger().info("Using prop file:"+ CREDS_PROP_FILE);
			props.load(getClass().getClassLoader().getResourceAsStream(CREDS_PROP_FILE));
			LogManager.getLogger().info("Done");
			LogManager.getLogger().info("Properties loaded = "+props);
		} catch (IOException e) {
			LogManager.getLogger().error("Couldn't load "+CREDS_PROP_FILE+" file");
			LogManager.getLogger().error("Please make sure the file exists and/or is in your classpath!");
		}
	}
	
	public static AppContext newInstance(){
		if(appContext == null){
			appContext = new AppContext();
		}
		return appContext;
	}
	
	public static AppContext getDefaultContext(){
		return appContext;
	}

	public String getProperty(String propName){
		return props.getProperty(propName);
	}
}
