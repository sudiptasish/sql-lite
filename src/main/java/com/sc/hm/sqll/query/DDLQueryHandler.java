package com.sc.hm.sqll.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.util.JdbcUtil;
import com.sc.hm.sqll.util.ResultSetHandler;

public class DDLQueryHandler extends QueryHandler {
	
	DDLQueryHandler() {
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
			
			preparedStatement = connection.prepareStatement(query.getQuery());
			List<Class<?>> paramTypes = query.getParamTypes();
			List<Object> paramValues = query.getParamValues();
			
			for (int i = 0; i < paramTypes.size(); i ++) {
				Class<?> paramType = paramTypes.get(i);
				Object paramValue = paramValues.get(i);
				
				if (String.class == paramType) {
					preparedStatement.setString(i + 1, (String)paramValue);
				}
				else if (Integer.class == paramType) {
					preparedStatement.setInt(i + 1, (Integer)paramValue);
				}
			}			
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
