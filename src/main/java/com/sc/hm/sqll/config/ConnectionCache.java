package com.sc.hm.sqll.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.sc.hm.sqll.util.JdbcUtil;

public class ConnectionCache {
	
	private static final long RETENTION = 15 * 60 * 1000;	// 15 mins.

	private final ConnectionConfig config;
	private Connection connection = null;
	
	private long lastUpdateTime = 0L;
	
	public ConnectionCache(ConnectionConfig config) {
		this.config = config;
	}
	
	/**
	 * Return the connection configuration.
	 * @return ConnectionConfig
	 */
	public ConnectionConfig getConfig() {
		return config;
	}

	/**
	 * Get a connection to the underlying database.
	 * This API will check if a connection exists in the cache, if so
	 * then return the cached connection. Otherwise create and open
	 * a new connection.
	 *  
	 * @return Connection
	 * @throws SQLException 
	 */
	public Connection getConnection() throws SQLException {
		if (connection == null
				|| connection.isClosed()
				|| !connection.isValid(0)
				|| System.currentTimeMillis() - lastUpdateTime > RETENTION) {
			
			if (connection != null && connection.isValid(0) && !connection.isClosed()) {
				connection.rollback();
			}
			createConnection();
		}
		return connection;
	}
	
	/**
	 * Just return the underlying connection.
	 * @return Connection
	 */
	public Connection getNoCheck() {
		return connection;
	}
	
	/**
	 * Create a new connection and store it.
	 * @throws SQLException 
	 */
	private void createConnection() throws SQLException {
        connection = JdbcUtil.getConnection(config);
        connection.setAutoCommit(false);
        lastUpdateTime = System.currentTimeMillis();
	}
}
