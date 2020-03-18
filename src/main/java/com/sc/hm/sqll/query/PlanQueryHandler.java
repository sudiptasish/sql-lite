package com.sc.hm.sqll.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Random;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.event.SharedObject;
import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.util.JdbcUtil;
import com.sc.hm.sqll.util.ResultSetHandler;

public class PlanQueryHandler extends QueryHandler {
	
	private final Random r = new Random();
	private final String EXPLAIN_QUERY = "EXPLAIN PLAN SET STATEMENT_ID = ''{0}'' FOR {1}";
	private final String PLAN_QUERY = "SELECT PLAN_TABLE_OUTPUT FROM TABLE(DBMS_XPLAN.DISPLAY(NULL, ''{0}'', ''{1}''))";
	
	PlanQueryHandler() {
		super();
	}

	@Override
	public TableData query(ConnectionCache cache, Query query) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		TableData data = new TableData();
		data.setPlan(true);
		
		try {
			long startTime = System.currentTimeMillis();
			String statementId = String.valueOf("A" + startTime);
			
			Connection connection = cache.getConnection();
			
			String finalQuery = MessageFormat.format(EXPLAIN_QUERY, statementId, query.getQuery());
			preparedStatement = connection.prepareStatement(finalQuery);
			boolean flag = preparedStatement.execute();
			
			// At this point, the query is "explained".
			// [ For any query syntax error, the sql exception will be thrown ].
			
			// Now fetch the plan from the plan table.
			String planType = (String)SharedObject.get().get(SharedObject.PLAN_TYPE_KEY);
			planType = planType == null ? "BASIC" : planType;
			
			finalQuery = MessageFormat.format(PLAN_QUERY, statementId, planType);
			preparedStatement = connection.prepareStatement(finalQuery);
			flag = preparedStatement.execute();
			
			if (flag) {
				resultSet = preparedStatement.getResultSet();
				ResultSetHandler.newResultSetHandler().handleResultSet(resultSet, data, 99999);
			}
			data.getStatistics().setExecutionTime(System.currentTimeMillis() - startTime);
			
			return data;
		}
		finally {
			JdbcUtil.closeAll(resultSet, preparedStatement);
		}
	}
}
