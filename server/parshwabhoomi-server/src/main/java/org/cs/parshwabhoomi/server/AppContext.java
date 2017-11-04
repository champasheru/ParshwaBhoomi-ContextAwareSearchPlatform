package org.cs.parshwabhoomi.server;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.DaoProviderFactory;
import org.cs.parshwabhoomi.server.dao.RawDaoProvider;

//Singleton
public class AppContext {
	private static final String CREDS_PROP_FILE="pb.properties";
	
	public static final String GOOGLE_API_KEY="org.cs.parshwabhoomi.google.api.key";
	public static final String GOOGLE_CSE_ID="org.cs.parshwabhoomi.google.cse.id";
	
	public static final String PB_JDBC_URL="org.cs.parshwabhoomi.db.url";
	public static final String PB_JDBC_DRIVER="org.cs.parshwabhoomi.db.driver";
	public static final String PB_JDBC_USERNAME="org.cs.parshwabhoomi.db.username";
	public static final String PB_JDBC_PASSWORD="org.cs.parshwabhoomi.db.password";
	
	public static final String PB_DAO_IMPL_PACKAGE="org.cs.parshwabhoomi.server.dao.raw.impl";
	
	private static AppContext appContext;
	 
	private Properties props;
	
	private RawDaoProvider daoProvider;
	
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
		
		Properties connectionProps = new Properties();
		connectionProps.setProperty(RawDaoProvider.CONN_DRIVER, props.getProperty(AppContext.PB_JDBC_DRIVER));
		connectionProps.setProperty(RawDaoProvider.CONN_URL, props.getProperty(AppContext.PB_JDBC_URL));
		connectionProps.setProperty(RawDaoProvider.CONN_USERNAME, props.getProperty(AppContext.PB_JDBC_USERNAME));
		connectionProps.setProperty(RawDaoProvider.CONN_PASSWORD, props.getProperty(AppContext.PB_JDBC_PASSWORD));
		daoProvider = DaoProviderFactory.create(PB_DAO_IMPL_PACKAGE, connectionProps);
	}
	
	public static AppContext newInstance(){
		if(appContext == null){
			appContext = new AppContext();
		}
		return appContext;
	}
	
//	public static AppContext getDefaultContext(){
//		return appContext;
//	}
	
	public static AppContext getDefaultContext(){
		if(appContext == null){
			appContext = new AppContext();
		}
		return appContext;
	}

	public String getProperty(String propName){
		return props.getProperty(propName);
	}

	/**
	 * @return the daoProvider
	 */
	public RawDaoProvider getDaoProvider() {
		return daoProvider;
	}
	
}
