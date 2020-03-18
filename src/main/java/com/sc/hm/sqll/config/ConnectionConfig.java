package com.sc.hm.sqll.config;

import java.io.Serializable;

/**
 * Connection config class encapsulates the connection details.
 * SQLLiTE uses type 4 jdbc driver for establishing connection to any RDBMS
 * system, and all the attributes required to make the remote connection are stored
 * in this class.
 * 
 * @author Sudiptasish Chanda
 */
public final class ConnectionConfig implements Serializable {

	private final String name;
    private final DatabaseType dbType;
	private final String host;
	private final String port;
	private final String sid;
	private final String user;
	private final String password;
	private final String serviceName;
	
	public ConnectionConfig(String name
        , DatabaseType dbType
        , String host
        , String port
        , String sid
        , String serviceName
        , String user
        , String password) {
        
		this.name = name;
        this.dbType = dbType;
		this.host = host;
		this.port = port;
		this.sid = sid;
		this.serviceName = serviceName;
		this.user = user;
		this.password = password;
	}

	/**
	 * Return the connection name.
	 * @return String
	 */
	public String getName() {
		return name;
	}

    /**
     * Return the db type, oracle, sybase, db2, etc.
     * @return String
     */
    public DatabaseType getDbType() {
        return dbType;
    }

	/**
	 * Return the database host name.
	 * @return String
	 */
	public String getHost() {
		return host;
	}

	/**
	 * Return the database port name.
	 * @return String
	 */
	public String getPort() {
		return port;
	}

	/**
	 * Return the database sid.
	 * @return String
	 */
	public String getSid() {
		return sid;
	}

	/**
	 * Return the database user.
	 * @return String
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Return the database password.
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Return the service name.
	 * @return String
	 */
	public String getServiceName() {
		return serviceName;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
