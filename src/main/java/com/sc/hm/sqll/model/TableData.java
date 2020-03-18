package com.sc.hm.sqll.model;

import java.util.ArrayList;
import java.util.List;

import com.sc.hm.sqll.query.QueryStatistics;

public class TableData {
	
	// Default statistics object that comes with this object.
	private QueryStatistics statistics = new QueryStatistics();
	
	private boolean isPlan = false;

	private Class[] classes = new Class[0];
	private String[] columns = new String[0];
	private List<Object[]> rows = new ArrayList<>(0);
	
	public TableData() {}

	public TableData(String[] columns, List<Object[]> rows) {
		this.columns = columns;
		this.rows = rows;
	}

	/**
	 * Return the set of select columns.
	 * @return String[]
	 */
	public String[] getColumns() {
		return columns;
	}

	/**
	 * Set the set of columns in this table data object.
	 * @param columns
	 */
	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	/**
	 * Return the list of rows of a select query output.
	 * @return List
	 */
	public List<Object[]> getRows() {
		return rows;
	}

	/**
	 * Set the list of rows.
	 * @param rows
	 */
	public void setRows(List<Object[]> rows) {
		this.rows = rows;
	}

	/**
	 * Return the data types corresponding to the columns.
	 * @return Class[]
	 */
	public Class[] getClasses() {
		return classes;
	}

	/**
	 * Set the data type of the columns.
	 * @param classes
	 */
	public void setClasses(Class[] classes) {
		this.classes = classes;
	}

	/**
	 * Get the query statistics.
	 * @return QueryStatistics
	 */
	public QueryStatistics getStatistics() {
		return statistics;
	}

	/**
	 * Set a new query statistics object.
	 * @param statistics
	 */
	public void setStatistics(QueryStatistics statistics) {
		this.statistics = statistics;
	}

	/**
	 * Check to see if this table data object is capturing 
	 * the query plan.
	 * @return boolean
	 */
	public boolean isPlan() {
		return isPlan;
	}

	/**
	 * Enable capturing plan.
	 * @param isPlan
	 */
	public void setPlan(boolean isPlan) {
		this.isPlan = isPlan;
	}
	
	/**
	 * Return the execution plan (if any).
	 * @return String
	 */
	public String getExecutionPlan() {
		StringBuilder _buff = new StringBuilder(rows.size() * 20);
		//_buff.append("<html>");
		for (int i = 0; i < rows.size(); i ++) {
			Object[] row = rows.get(i);
			_buff.append("\n").append(row[1].toString());
		}
		//_buff.append("</html>");
		return _buff.toString();
	}
}
