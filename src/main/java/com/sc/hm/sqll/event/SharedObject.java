package com.sc.hm.sqll.event;

import java.util.HashMap;
import java.util.Map;

public class SharedObject {
	
	private static final SharedObject INSTANCE = new SharedObject();
	
	public static final String PLAN_TYPE_KEY = "P_T_K"; 

	private Map<String, Object> params = new HashMap<>();
	
	private SharedObject() {}
	
	/**
	 * Return the singleton shared object.
	 * @return
	 */
	public static SharedObject get() {
		return INSTANCE;
	}
	
	/**
	 * Put the parameter key and value.
	 * @param key
	 * @param val
	 */
	public void put(String key, Object value) {
		params.put(key, value);
	}
	
	/**
	 * Return the value for this key.
	 * @param key
	 * @return Object
	 */
	public Object get(String key) {
		return params.get(key);
	}
	
	/**
	 * Clear the shared object map.
	 */
	public void clear() {
		params.clear();
	}
}
