package com.sc.hm.sqll.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.util.JdbcUtil;
import com.sc.hm.sqll.util.ResultSetHandler;

public class DefaultQueryHandler extends QueryHandler {
	
	DefaultQueryHandler() {
		super();
	}

	@Override
	public TableData query(ConnectionCache cache, Query query) throws SQLException {
		if (QueryHandler.SELECT_QUERY == query.getType()) {
			return select(cache, query);
		}
		throw new IllegalArgumentException("Invalid query type " + query.getType());
	}
	
	/**
	 * Perfor a select operation.
	 * 
	 * @param cache
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	private TableData select(ConnectionCache cache, Query query) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		TableData data = new TableData();
		try {
			long startTime = System.currentTimeMillis();			
			Connection connection = cache.getConnection();
			
			String sqlQuery = query.getQuery();
			
			/*String tmp = sqlQuery.substring(sqlQuery.length() - 15).toUpperCase();
			  if (!tmp.contains("ROWNUM") && !tmp.contains("ROWS ONLY")) {
			    sqlQuery = sqlQuery + " " + MessageFormat.format(PAGINATION_CLAUSE, String.valueOf(0), String.valueOf(query.getLimit()));
			  }*/
			
			preparedStatement = connection.prepareStatement(sqlQuery);
			preparedStatement.setFetchSize(FETCH_SIZE);
			boolean flag = preparedStatement.execute();
			
			// Check if this query is supposed to return any result set.
			// This will be determined by the returned flag.
			// If the flag is set to 'true', which means it has some result,
			// therefore try fetching the result set.
			if (flag) {
				resultSet = preparedStatement.getResultSet();
				ResultSetHandler.newResultSetHandler().handleResultSet(resultSet, data, query.getLimit());
			}
			data.getStatistics().setExecutionTime(System.currentTimeMillis() - startTime);
			
			return data;
		}
		finally {
			JdbcUtil.closeAll(resultSet, preparedStatement);
		}
	}
}
