package com.sc.hm.sqll.config;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Utility class to read the configuration file and construct the connection
 * configuration. The individual configuration are store in {@link ConnectionConfig}
 * class.
 * 
 * @author Sudiptasish Chanda
 */
public final class ConfigurationUtil {

	// Underlying configuration manager, that is responsible for reading and
    // parsing the configuration file and initialize the system.
	private static final ConfigurationManager CONFIG_MANAGER = new SQLLConfigManager();
    
    public static final String DEFAULT_CONFIG = "config/db-config.xml";
	
    /**
     * API to configure the system.
     * It reads the database connection details from the XML file and keep it in
     * local cache. It is, however, possible to override the default config file,
     * by providing the same via system property: db.config.path.
     * 
     * If no file is found in the classpath or in the specified directory, system
     * will fail to start.
     */
	public static void configure() {
		try {
			String configFilePath = System.getProperty("db.config.path");
			if (configFilePath == null) {
			    configFilePath = DEFAULT_CONFIG;
			}
			File file = new File(configFilePath);
			if (!file.exists()) {				
				InputStream iStream = ClassLoader.getSystemClassLoader().getResourceAsStream(configFilePath);
				if (iStream.available() == 0) {
					throw new Exception("Configuration File Not Found... Exiting from the System!!!!");
				}
				else {
					if (iStream != null) {
						iStream.close();
					}
				}
			}
			CONFIG_MANAGER.configure(configFilePath);
		}
		catch (Exception e) {
            e.printStackTrace();
			System.err.println("Configuration File 'db-config.xml' Not Found in the Classpath.");
			System.exit(-1);
		}
	}
	
	/**
	 * Return an iterator over the list of all configuration objects.
	 * @return Iterator
	 */
	public static Iterator<ConnectionConfig> getAllConfigs() {
		return CONFIG_MANAGER.getAll();
	}
	
	/**
	 * Return the configuration object associated with this name/key.
	 * @param name
	 * @return ConnectionConfig
	 */
	public static ConnectionConfig getConfig(String name) {
		return CONFIG_MANAGER.get(name);
	}
	
	/**
	 * Put the configuration details against the name/key in the cache.
	 * @param name		Connection name
	 * @param config	Connection details
	 */
	public static void putConfig(String name, ConnectionConfig config) {
		CONFIG_MANAGER.put(name, config);
	}
	
	/**
	 * Remove the configuration from the cache for this name/key.
	 * @param name
	 */
	public static void removeConfig(String name) {
		CONFIG_MANAGER.remove(name);
	}
	
	/**
	 * Clear the environment cache. 
	 */
	public static void clearConfig() {
		CONFIG_MANAGER.clear();
	}
}
