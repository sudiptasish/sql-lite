package com.sc.hm.sqll.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.util.JdbcUtil;
import com.sc.hm.sqll.util.ResultSetHandler;

public class DESCQueryHandler extends QueryHandler {
	
	DESCQueryHandler() {
		super();
	}

	@Override
	public TableData query(ConnectionCache cache, Query query) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		TableData data = new TableData();
		try {
			long startTime = System.currentTimeMillis();			
			Connection connection = cache.getConnection();
			
			String sqlQuery = query.getQuery();
			String[] _arr = sqlQuery.split(" ");
			if (_arr.length != 2) {
				throw new IllegalArgumentException("Invalid SQL Query");
			}
			preparedStatement = connection.prepareStatement(getDescribeTableQuery());
			preparedStatement.setString(1, _arr[1].trim().toUpperCase());
			preparedStatement.setFetchSize(FETCH_SIZE);
			
			boolean flag = preparedStatement.execute();
			
			// Check if this query is supposed to return any result set.
			// This will be determined by the returned flag.
			// If the flag is set to 'true', which means it has some result,
			// therefore try fetching the result set.
			if (flag) {
				resultSet = preparedStatement.getResultSet();
				ResultSetHandler.newResultSetHandler().handleResultSet(resultSet, data, 1001);
			}
			data.getStatistics().setExecutionTime(System.currentTimeMillis() - startTime);
			
			return data;
		}
		finally {
			JdbcUtil.closeAll(resultSet, preparedStatement);
		}
	}
}
