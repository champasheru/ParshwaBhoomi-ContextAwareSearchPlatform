/**
 * parshwabhoomi-server	11-Jul-2017:7:51:35 PM
 * saurabh
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class AbstractRawDao implements RawDao {
	protected Connection connection;
	private boolean txnInProgress;

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#set(java.lang.Object)
	 */
	public void set(Connection connection) {
		this.connection = connection;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#get()
	 */
	public Connection get() {
		return connection;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#close()
	 */
	public void close() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Not able to close JDBC connection: "+connection);
			LogManager.getLogger().error("Exception : "+e);
		}finally{
			connection = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#beginTransaction()
	 * If underlying connection's auto commit mode is set to true, then this method is a no-op.
	 * @see java.sql.Connection for more details.
	 */
	public void beginTransaction() {
		try{
			if(!connection.getAutoCommit()){
				txnInProgress = true;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			txnInProgress = false;
			LogManager.getLogger().error("Can't being transaction on connection: "+connection);
			LogManager.getLogger().error("Exception : "+e);
		}
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#commitTransaction()
	 * If underlying connection's auto commit mode is set to true, then this method is a no-op.
	 * @see java.sql.Connection for more details.
	 */
	public void commitTransaction() {
		try{
			if(!isAutoCommit()){
				connection.commit();
			}
		}catch (SQLException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't commit ongoing transaction on connection: "+connection);
			LogManager.getLogger().error("Exception : "+e);
			LogManager.getLogger().error("Rolling back ongoing transaction... ");
			rollbackTransaction();
		}finally{
			txnInProgress = false;
		}
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#rollbackTransaction()
	 * If underlying connection's auto commit mode is set to true, then this method is a no-op.
	 * @see java.sql.Connection for more details.
	 */
	public void rollbackTransaction() {
		try{
			if(!isAutoCommit()){
				connection.rollback();
			}
		}catch (SQLException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't rollback ongoing transaction on connection: "+connection);
			LogManager.getLogger().error("Exception : "+e);
		}finally{
			txnInProgress = false;
		}
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#hasActiveTransaction()
	 * If underlying connection's auto commit mode is set to true, then this method is irrelevant and will always return false.
	 * @see java.sql.Connection for more details.
	 */
	public boolean hasActiveTransaction() {
		return txnInProgress;
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#setAutoCommit(boolean)
	 * If underlying connection's auto commit mode was off before calling this method and if the mode is requested to be turned on, then this method will
	 * first commit the changes pending for the ongoing transaction and subsequent call to hasActiveTransaction() will return false.
	 * If the requested mode and existing mode are unchanged, this method is no-op.
	 * @see java.sql.Connection#setAutoCommit for more details.
	 */
	public void setAutoCommit(boolean autoCommit) {
		try {
			if(isAutoCommit() == autoCommit){
				//no-op
				return;
			}
			//connection was not in autocommit mode earlier and now turning it on
			if(!isAutoCommit() && hasActiveTransaction()){
				//Make sure you commit ongoing transaction 1st
				LogManager.getLogger().warn("Reuest to enable auto-commit mode;commiting ongoing transaction first...");
				commitTransaction();
			}
			connection.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Can't change auto commit mode on underlying JDBC connection: "+connection);
			LogManager.getLogger().error("Exception : "+e);
		}
	}

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.Dao#getAutoCommit()
	 */
	public boolean isAutoCommit() {
		boolean autoCommit = false;
		try {
			autoCommit = connection.getAutoCommit();
		} catch (SQLException e) {
			e.printStackTrace();
			LogManager.getLogger().error("Unable to get auto commit mode on underlying JDBC connection: "+connection);
			LogManager.getLogger().error("Exception : "+e);
		}
		return autoCommit;
	}

}
