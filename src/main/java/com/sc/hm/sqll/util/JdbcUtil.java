package com.sc.hm.sqll.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import com.sc.hm.sqll.config.ConnectionConfig;
import com.sc.hm.sqll.config.DatabaseType;

public class JdbcUtil {
	
	private static final String ORACLE_SID_URL = "jdbc:oracle:thin:@{0}:{1}:{2}";
	private static final String ORACLE_SERVICE_URL = "jdbc:oracle:thin:@{0}:{1}/{2}";
    
    private static final String DB2_URL = "jdbc:db2://{0}:{1}/{2}";
    private static final String SYSBASE_URL = "jdbc:sybase:Tds:{0}:{1}/{2}";
    private static final String DERBY_URL = "jdbc:derby://{0}:{1}/{2}";
	
	/**
	 * Test to see if the database configuration is valid.
	 * This is done by obtaining a valid connection to the underlying db.
	 * 
	 * @param config
	 */
	public static void testConnection(ConnectionConfig config) throws SQLException {
		Connection connection = null;
		try {
			connection = getConnection(config);
		}
		finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
	
	/**
	 * Obtain a new connection.
	 * 
	 * @param config
	 * @return Connection
	 * @throws SQLException 
     * @throws ClassNotFoundException 
	 */
	public static Connection getConnection(ConnectionConfig config)
        throws SQLException {
        
        String urlTemplate = "";
        String sidOrService = "";
        
        if (DatabaseType.ORACLE == config.getDbType()) {
            urlTemplate = ORACLE_SID_URL;
            sidOrService = config.getSid();
		
            if (config.getServiceName() != null && config.getServiceName().length() > 0) {
                urlTemplate = ORACLE_SERVICE_URL;
                sidOrService = config.getServiceName();
            }
        }
        else if (DatabaseType.DB2 == config.getDbType()) {
            urlTemplate = DB2_URL;
            sidOrService = config.getSid();
        }
        else if (DatabaseType.SYSBASE == config.getDbType()) {
            urlTemplate = SYSBASE_URL;
            sidOrService = config.getSid();
        }
        else if (DatabaseType.DERBY == config.getDbType()) {
            urlTemplate = DERBY_URL;
            sidOrService = config.getSid();
        }
        
        if (config.getUser() != null && config.getUser().length() > 0) {
            return DriverManager.getConnection(
				MessageFormat.format(urlTemplate
						, config.getHost()
						, config.getPort()
						, sidOrService
				)
				, config.getUser()
				, config.getPassword());
        }
        else {
            return DriverManager.getConnection(
				MessageFormat.format(urlTemplate
						, config.getHost()
						, config.getPort()
						, sidOrService
				));
        }
	}

	/**
	 * Close the database resources.
	 * 
	 * @param resultSet
	 * @param statement
	 */
	public static void closeAll(ResultSet resultSet, Statement statement) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
		}
		catch (SQLException e) {
			// Do Nothing
		}
	}
}
