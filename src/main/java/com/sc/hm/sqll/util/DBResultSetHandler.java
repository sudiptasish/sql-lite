package com.sc.hm.sqll.util;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.query.QueryHandler;

public class DBResultSetHandler extends ResultSetHandler {
	
	private final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");

	/**
	 * 
	 */
	DBResultSetHandler() {
		super();
	}

	@Override
	public void handleResultSet(ResultSet resultSet, TableData data) throws SQLException {		
		handleResultSet(resultSet, data, QueryHandler.LIMIT);
	}

	@Override
	public void handleResultSet(ResultSet resultSet, TableData data, int limit) throws SQLException {
		long startTime = System.currentTimeMillis();
		ResultSetMetaData rsMetadata = resultSet.getMetaData();
		
		String[] columns = new String[rsMetadata.getColumnCount() + 1];
		Class[] classes = new Class[rsMetadata.getColumnCount() + 1];
		int[] types = new int[rsMetadata.getColumnCount() + 1];
		int[] precision = new int[rsMetadata.getColumnCount() + 1];
		int[] scale = new int[rsMetadata.getColumnCount() + 1];
		
		classes[0] = Integer.class;
		types[0] = Types.NUMERIC;
		
		for (int index = 1; index < columns.length; index ++) {
			columns[index] = rsMetadata.getColumnName(index);
			types[index] = rsMetadata.getColumnType(index);
			if (types[index] == Types.INTEGER) {
				precision[index] = rsMetadata.getPrecision(index);
				scale[index] = rsMetadata.getScale(index);
			}
			else {
				precision[index] = 0;
				scale[index] = 0;
			}
		}
		determineColumnClass(types, classes);
		
		List<Object[]> rows = new ArrayList<>();
		int rowCount = 0;
		
		while (resultSet.next()) {
			Object[] table_data = new Object[columns.length];
			table_data[0] = ++ rowCount;
			
			for (int index = 1; index < columns.length; index ++) {
				if (types[index] == Types.ROWID || types[index] == Types.VARBINARY) {
					table_data[index] = resultSet.getString(index);
				}
				else if (types[index] == Types.VARCHAR || types[index] == Types.LONGVARCHAR) {
					table_data[index] = resultSet.getString(index);
				}
				else if (types[index] == Types.NUMERIC) {
					try {
						if (precision[index] > 8) {
							if (scale[index] > 0) {
								table_data[index] = resultSet.getDouble(index);
							}
							else {
								table_data[index] = resultSet.getLong(index);
							}
						}
						else {
							if (scale[index] > 0) {
								table_data[index] = resultSet.getDouble(index);
							}
							else {
								table_data[index] = resultSet.getInt(index);
							}
						}
					}
					catch (SQLException e) {
						if (e.getMessage().contains("Numeric Overflow")) {
							table_data[index] = resultSet.getBigDecimal(index);
						}
					}
					if (resultSet.wasNull()) {
						table_data[index] = null;
					}
				}
				else if (types[index] == Types.DOUBLE || types[index] == Types.FLOAT) {
					table_data[index] = resultSet.getDouble(index);
				}
				else if (types[index] == Types.TIMESTAMP) {
					Timestamp ts = resultSet.getTimestamp(index);
					if (ts != null) {
						table_data[index] = formatter.format(ts);
					}
				}
				else if (types[index] == Types.DATE) {
					Date ts = resultSet.getDate(index);
					if (ts != null) {
						table_data[index] = formatter.format(ts);
					}
				}
				else if (types[index] == Types.TIME) {
					table_data[index] = resultSet.getTime(index);
				}
				else if (types[index] == Types.CLOB) {
					table_data[index] = resultSet.getString(index);
				}
				else {
					table_data[index] = resultSet.getObject(index);
				}
			}
			rows.add(table_data);
			
			if (rowCount == limit) {
				data.setColumns(columns);
				data.setRows(rows);
				data.setClasses(classes);
				data.getStatistics().setFetchTime(System.currentTimeMillis() - startTime);
				return;
			}
		}
		data.setColumns(columns);
		data.setRows(rows);
		data.setClasses(classes);
		data.getStatistics().setFetchTime(System.currentTimeMillis() - startTime);
	}
	
	/**
	 * Determine the column class from the sql types.
	 * 
	 * @param types
	 * @param classes
	 */
	private void determineColumnClass(int[] types, Class[] classes) {
		for (int i = 0; i < types.length; i ++) {
			if (types[i] == Types.ROWID || types[i] == Types.VARBINARY) {
				classes[i] = String.class;
			}
			else if (types[i] == Types.VARCHAR || types[i] == Types.LONGVARCHAR) {
				classes[i] = String.class;
			}
			else if (types[i] == Types.NUMERIC || types[i] == Types.INTEGER) {
				classes[i] = Integer.class;
			}
			else if (types[i] == Types.FLOAT || types[i] == Types.DOUBLE) {
				classes[i] = Float.class;
			}
			else if (types[i] == Types.TIMESTAMP) {
				classes[i] = String.class;
			}
			else if (types[i] == Types.DATE) {
				classes[i] = String.class;
			}
			else if (types[i] == Types.TIME) {
				classes[i] = Time.class;
			}
			else {
				classes[i] = String.class;
			}
		}
	}
}

