package com.sc.hm.sqll.query;

import java.util.ArrayList;
import java.util.List;

public class Query {

	private int type = -1;
	private String query;
	private int limit = 100;
	
	private List<Class<?>> paramTypes = new ArrayList<>();
	private List<Object> paramValues = new ArrayList<>();
	
	public Query() {}

	public Query(String query) {
		this(query, QueryHandler.SELECT_QUERY, 100);
	}

	public Query(String query, int limit) {
		this(query, QueryHandler.SELECT_QUERY, limit);
	}

	public Query(String query, int type, int limit) {
		this.query = query;
		this.type = type;
		this.limit = limit;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public List<Class<?>> getParamTypes() {
		return paramTypes;
	}

	public List<Object> getParamValues() {
		return paramValues;
	}
	
	public void addParameter(Class<?> clazz, Object param) {
		paramTypes.add(clazz);
		paramValues.add(param);
	}
}
