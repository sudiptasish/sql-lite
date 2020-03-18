package com.sc.hm.sqll.config;


public class WorksheetConfig {

	private final ConnectionConfig config;
	
	public WorksheetConfig(ConnectionConfig config) {
		this.config = config;
	}

	/**
	 * Return the context of the associated worksheet.
	 * @return String
	 */
	public String getContext() {
		return config.getName();
	}

	/**
	 * Return the connection configuration.
	 * @return ConnectionConfig
	 */
	public ConnectionConfig getConfig() {
		return config;
	}
}
