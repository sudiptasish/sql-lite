package com.sc.hm.sqll.config;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConnectionRepos {

	private static final ConnectionRepos INSTANCE = new ConnectionRepos();
	
	// Map to hold the connection cache objects.
	private ConcurrentMap<String, ConnectionCache> caches = new ConcurrentHashMap<>();
	
	private ConnectionRepos() {}
	
	/**
	 * Return the singleton repository instance.
	 * @return
	 */
	public static ConnectionRepos getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Add the connection cache details into the map.
	 * @param name
	 * @param cache
	 */
	public void addConnectionCache(String name, ConnectionCache cache) {
		caches.put(name, cache);
	}
	
	/**
	 * Remove the connection cache for this connection name.
	 * @param name
	 * @return ConnectionCache
	 */
	public ConnectionCache remove(String name) {
		return caches.remove(name);
	}
	
	/**
	 * Return an iterator over the list of all available connection caches
	 * @return Iterator
	 */
	public Iterator<Map.Entry<String, ConnectionCache>> all() {
		return caches.entrySet().iterator();
	}
	
	/**
	 * Clear all the caches.
	 */
	public void clear() {
		caches.clear();
	}
}
