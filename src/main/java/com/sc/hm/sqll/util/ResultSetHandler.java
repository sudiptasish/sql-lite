package com.sc.hm.sqll.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sc.hm.sqll.model.TableData;

public abstract class ResultSetHandler {

	protected ResultSetHandler() {}
	
	/**
	 * Return a new instance of ResultSetHandler
	 * @return	ResultSetHandler
	 */
	public static ResultSetHandler newResultSetHandler() {
		return new DBResultSetHandler();
	}
	
	/**
	 * Iterate through the result set and return the record object list.
	 * 
	 * @param 	resultSet
	 * @return	Object[][]
	 */
	public abstract void handleResultSet(ResultSet resultSet, TableData data) throws SQLException;
	
	/**
	 * Iterate through the result set and return the record object list.
	 * 
	 * @param 	resultSet
	 * @param	limt
	 * @return	Object[][]
	 */
	public abstract void handleResultSet(ResultSet resultSet, TableData data, int limit) throws SQLException;
	
}

