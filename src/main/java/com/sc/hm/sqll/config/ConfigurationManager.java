package com.sc.hm.sqll.config;

import java.util.Iterator;

/**
 * Interface that represents the configuration manager.
 * It exposes several APIs to read and parse the configuration file to initialize
 * the system.
 * 
 * @author Sudiptasish Chanda
 */
public interface ConfigurationManager {

	/**
	 * Configure the SQL Lite.The method will read all the existing db configurations
     * from the file specified.
	 * 
	 * @param file
     * @throws Exception
	 */
	void configure(String file) throws Exception;
	
	/**
	 * Return an iterator over the list of all configuration objects.
	 * @return Iterator
	 */
	Iterator<ConnectionConfig> getAll();
	
	/**
	 * Return the configuration object associated with this name/key.
	 * @param name
	 * @return ConnectionConfig
	 */
	ConnectionConfig get(String name);
	
	/**
	 * Put the configuration details against the name/key in the cache.
	 * @param name		Connection name
	 * @param config	Connection details
	 */
	void put(String name, ConnectionConfig config);
	
	/**
	 * Remove the configuration from the cache for this name/key.
	 * @param name
	 */
	void remove(String name);
	
	/**
	 * Clear the environment cache. 
	 */
	void clear();
}
