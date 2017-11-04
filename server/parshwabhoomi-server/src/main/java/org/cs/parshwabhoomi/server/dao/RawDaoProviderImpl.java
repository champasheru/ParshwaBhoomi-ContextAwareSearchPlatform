package org.cs.parshwabhoomi.server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

import org.cs.parshwabhoomi.server.dao.DaoProviderFactory.DaoImplementationStrategy;

//TODO: Provide one more variant for DataSource based connection pool compliant JDBC connection management or accommodate it in the same class.
//Refer:
//c3p0
//http://www.mchange.com/projects/c3p0
//https://github.com/swaldman/c3p0
//Apache DBCP
//http://commons.apache.org/proper/commons-dbcp/

public class RawDaoProviderImpl implements RawDaoProvider {
	//DAO base package name
	private String packageName;
	private Properties connProperties;
	private boolean open;
	private DaoImplementationStrategy strategy;
	
	
	public RawDaoProviderImpl(String packageName, Properties connProperties) {
		this(packageName, connProperties, DaoImplementationStrategy.PER_INSTANCE);
	}
	
	
	public RawDaoProviderImpl(String packageName, Properties connProperties, DaoImplementationStrategy strategy) {
		this.packageName = packageName;
		this.connProperties = connProperties;
		this.strategy = strategy;
		open = true;
	}
	
	
	public DaoImplementationStrategy getImplementationStrategy(){
		return strategy;
	}


	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.DaoProvider#getDAO(java.lang.String)
	 */
	public RawDao getDAO(String classname) {
		return getDAO(classname, null);
	}


	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.DaoProvider#getDAO(java.lang.String, java.lang.Object)
	 */
	
	@SuppressWarnings("unchecked")
	public RawDao getDAO(String classname, RawDao existingDAO) {
		RawDao dao = null;
		if(!isOpen()){
			return null;
		}
		classname = packageName+"."+classname;
		LogManager.getLogger().info("Returning the DAO for: "+classname);
		
		try {
			Class<? extends RawDao> daoClass = (Class<? extends RawDao>) Class.forName(classname);
			dao = daoClass.newInstance();
			
			if(existingDAO != null){
				LogManager.getLogger().info("Reusing existing DAO/Connection...");
				dao.set(((RawDao)existingDAO).get());
			}else{
				if(strategy == DaoImplementationStrategy.PER_THREAD){
					LogManager.getLogger().warn("Current strategy is: PER_THREAD, creating a new Connection per DAO is same as PER_INSTANCE; make sure you pass existing DAO to achieve the expected behavior!");
				}
				//Refer: https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
				/**
				 * DriverManager, the service provider mechanism and memory leaks

				java.sql.DriverManager supports the service provider mechanism. This feature is that all the available JDBC drivers that announce themselves by providing a META-INF/services/java.sql.Driver file are automatically discovered, loaded and registered, relieving you from the need to load the database driver explicitly before you create a JDBC connection. However, the implementation is fundamentally broken in all Java versions for a servlet container environment. The problem is that java.sql.DriverManager will scan for the drivers only once.

				The JRE Memory Leak Prevention Listener that is included with Apache Tomcat solves this by triggering the drivers scan during Tomcat startup. This is enabled by default. It means that only libraries visible to the listener such as the ones in $CATALINA_BASE/lib will be scanned for database drivers. If you are considering disabling this feature, note that the scan would be triggered by the first web application that is using JDBC, leading to failures when this web application is reloaded and for other web applications that rely on this feature.

				Thus, the web applications that have database drivers in their WEB-INF/lib directory cannot rely on the service provider mechanism and should register the drivers explicitly.

				The list of drivers in java.sql.DriverManager is also a known source of memory leaks. Any Drivers registered by a web application must be deregistered when the web application stops. Tomcat will attempt to automatically discover and deregister any JDBC drivers loaded by the web application class loader when the web application stops. However, it is expected that applications do this for themselves via a ServletContextListener.
				 */
				Class.forName(connProperties.getProperty(CONN_DRIVER));
				Connection conn = DriverManager.getConnection(connProperties.getProperty(CONN_URL), connProperties.getProperty(CONN_USERNAME), connProperties.getProperty(CONN_PASSWORD));
				dao.set(conn);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't create DAO!");
			LogManager.getLogger().error("Exception : "+e);
		} catch (InstantiationException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't create DAO!");
			LogManager.getLogger().error("Exception : "+e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't create DAO!");
			LogManager.getLogger().error("Exception : "+e);
		} catch (SQLException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't create DAO: not able to create a JDBC connection!");
			LogManager.getLogger().error("Exception : "+e);
		}
		
		return dao;
	}


	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.DaoProvider#getPackageName()
	 */
	public String getPackageName() {
		return packageName;
	}
	
	public boolean isOpen(){
		return open;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.DaoProvider#close()
	 */
	public void close() {
		if(isOpen()){
			open = false;
		}
	}

}
