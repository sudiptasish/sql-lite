package com.sc.hm.sqll.config;

import java.io.Serializable;

/**
 * Cache to store the connection config, that were just read from the configuration file.
 * Any newly created connection details will be kept in the cache, by the time
 * they are flushed to the disk.
 * 
 * @author Sudiptasish Chanda
 */
public class EnvironmentCache implements Serializable {

	private ConnectionConfig config = null;
	
	public EnvironmentCache() {}
	
	/**
	 * 
	 * @param config
	 */
	public EnvironmentCache(ConnectionConfig config) {
		this.config = config;
	}

	/**
	 * Return the connection config object.
	 * @return ConnectionConfig
	 */
	public ConnectionConfig getConfig() {
		return config;
	}

	/**
	 * Set the connection configuration.
	 * @param config
	 */
	public void setConfig(ConnectionConfig config) {
		this.config = config;
	}
	
	/**
	 * Clear the cache.
	 */
	public void clear() {
		this.config = null;
	}
}
