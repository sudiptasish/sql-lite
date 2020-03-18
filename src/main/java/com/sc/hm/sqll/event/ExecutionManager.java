package com.sc.hm.sqll.event;

import java.text.MessageFormat;

import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.query.QueryHandler;
import com.sc.hm.sqll.ui.Worksheet;

public abstract class ExecutionManager {

	// Stats Template.
	private final String STAT_TEMPLATE = " Total Record(s): {0}. Query Execution Time (ms): {1}. Fetch Time (ms): {2}. Render Time (ms): {3}.";
	
	// Default error handler object.
	private final ErrorHandler errHandler = new ErrorHandler();
	
	protected ExecutionManager() {}
	
	/**
	 * Create and return a new instance of default query
	 * execution manager.
	 * 
	 * @return DefaultExecutionManager
	 */
	public static ExecutionManager getDefaultManager() {
		return new DefaultExecutionManager();
	}
	
	/**
	 * Create and return a new instance of schema query
	 * execution manager.
	 * 
	 * @return SchemaExecutionManager
	 */
	public static ExecutionManager getSchemaManager() {
		return new SchemaExecutionManager();
	}
	
	/**
	 * Create and return a new instance of plan query
	 * execution manager.
	 * 
	 * @return PlanExecutionManager
	 */
	public static ExecutionManager getPlanManager() {
		return new PlanExecutionManager();
	}
	
	/**
	 * Create and return a new instance of DDL query
	 * execution manager.
	 * 
	 * @return DDLExecutionManager
	 */
	public static ExecutionManager getDDLManager() {
		return new DDLExecutionManager();
	}
	
	/**
	 * Check if this is a describe table query.
	 * @param query
	 * @return boolean
	 */
	protected boolean isDescribeQuery(String query) {
		return query.startsWith("DESC") || query.startsWith("desc");
	}
	
	/**
	 * Return the stats template of a query.
	 * @return String
	 */
	public String getStatTemplate() {
		return STAT_TEMPLATE;
	}

	/**
	 * Return the error handler object associated with this execution manager.
	 * @return ErrorHandler
	 */
	public ErrorHandler getErrorHandler() {
		return errHandler;
	}
	
	/**
	 * Return the query handler for this execution manager.
	 * @return QueryHandler
	 */
	public QueryHandler getQueryHandler() {
		return QueryHandler.getDefaultQueryHandler();
	}
	
	/**
	 * Return the describe query handler for this execution manager.
	 * @return QueryHandler
	 */
	public QueryHandler getDescQueryHandler() {
		return QueryHandler.getDescribeQueryHandler();
	}
	
	/**
	 * Return the transactional query handler for this execution manager.
	 * @return QueryHandler
	 */
	public QueryHandler getTxnQueryHandler() {
		return QueryHandler.getTxnQueryHandler();
	}
	
	/**
	 * Display the query execution statistics on the worksheet window.
	 * @param ws	Worksheet window
	 * @param data	Data.
	 */
	protected void displayStats(Worksheet ws, TableData data) {
		String statMsg = MessageFormat.format(getStatTemplate()
				, data.getRows().size()
				, data.getStatistics().getExecutionTime()
				, data.getStatistics().getFetchTime()
				, data.getStatistics().getRenderTime());
		
		ws.getStatLabel().setText(statMsg);
	}

	/**
	 * Prepare and execute any query coming from the worksheet window.
	 * Execution Manager is responsible for preparing the query, invoking
	 * the underlying query handler to populate the final result set
	 * and show it to client.
	 * 
	 * Execution Manager also has a error handler associated to it.
	 * In case the query fails to execute, the appropriate error (along
	 * with the stacktrace) will be shown to  user.
	 * 
	 * The query can be any DDL or DML query.
	 * 
	 * @param ws		Worksheet object.
	 * @param query		SQL Query to be executed.
	 * @param callback 	Callback object
	 */
	public abstract void prepare(Worksheet ws, String query, Object callback);
}
