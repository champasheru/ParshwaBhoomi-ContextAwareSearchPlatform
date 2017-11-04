package org.cs.parshwabhoomi.server.dao;

import org.cs.parshwabhoomi.server.dao.DaoProviderFactory.DaoImplementationStrategy;

public interface DaoProvider<DAO> {
	/**
	 * 
	 * @param classname
	 * @return the DAO instance for the corresponding class.
	 * The implementation shall provide the DAO instance with a new instance of EntityManager.
	 */
	DAO getDAO(String classname);
	
	/**
	 * 
	 * @param classname
	 * @param existingDAO
	 * @return  the DAO instance for the corresponding class constructed using the
	 * an existing instance of underlying DB machanism present in existing DAO.
	 * 
	 * use this variant when you want to provide an existing instance of underlying
	 * DB machanism (e.g. EntityManager in case of JpaDao or JDBC Connection in case of RawDao) 
	 * to the DAO. You may typically need this kind of scenario in case of transactions that
	 * span across multiple DAOs. Use this version only if necessary, default one will suffice
	 * for most of the purposes.
	 */
	DAO getDAO(String classname, DAO existingDAO);
	
	String getPackageName();
	
	DaoImplementationStrategy getImplementationStrategy();
	
	void close();
}
