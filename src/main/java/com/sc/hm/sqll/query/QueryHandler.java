package com.sc.hm.sqll.query;

import java.sql.SQLException;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.model.TableData;

public abstract class QueryHandler {
	
	public static final int COMMIT_QUERY = 0;
	public static final int ROLLBACK_QUERY = 1;
	public static final int SELECT_QUERY = 2;
	public static final int INSERT_QUERY = 3;
	public static final int UPDATE_QUERY = 4;
	public static final int DELETE_QUERY = 5;
	
	public static final int FETCH_SIZE = 100;
	public static final int LIMIT = 300;
	
	private final String DESCRIBE_KEYWORD = "DESC";

	private final String PAGINATION_CLAUSE = "OFFSET {0} ROWS FETCH NEXT {1} ROWS ONLY";
	
	private final String DESCRIBE_TABLE_QUERY =
			"SELECT column_name AS \"Name\"," +
			"	    DECODE (nullable, 'N', 'NOT NULL', 'NULL') AS \"Null ?\"," +
			"	    data_type || DECODE (data_type, 'VARCHAR2', '(' || data_length || ')', 'NUMBER', DECODE (data_precision, NULL, '', '(' || data_precision || DECODE (data_scale, NULL, '', 0, '', ', ' || TO_CHAR(data_scale)) || ')'), '' ) AS \"Type\"," +
			"	    data_default AS \"Default\"" +
			"	  FROM user_tab_columns" +
			"	WHERE table_name = ?" +
			"	 ORDER BY column_id";
	
	private final String OBJECTS_QUERY =
			"SELECT object_type, object_name" +
			"  FROM user_objects" +
            " WHERE object_type in ('TABLE', 'VIEW', 'TYPE', 'TYPE BODY', 'SYNONYM', 'SEQUENCE', 'TRIGGER', 'PROCEDURE', 'PACKAGE', 'PACKAGE BODY')" +
            " ORDER BY object_type, object_name";
	
	private final String TABLE_DDL_QUERY = "SELECT dbms_metadata.get_ddl(?, ?, ?) FROM DUAL";
	
	private static final QueryHandler defaultHandler = new DefaultQueryHandler();
	private static final QueryHandler descHandler = new DESCQueryHandler();
	private static final QueryHandler planHandler = new PlanQueryHandler();
	private static final QueryHandler ddlHandler = new DDLQueryHandler();
	private static final QueryHandler txnQueryHandler = new TransactionalQueryHandler();

	protected QueryHandler() {}
	
	/**
	 * Return the default Query Handler.
	 * @return DefaultQueryHandler
	 */
	public static QueryHandler getDefaultQueryHandler() {
		return defaultHandler;
	}
	
	/**
	 * Return the describe table Query Handler.
	 * @return DESCQueryHandler
	 */
	public static QueryHandler getDescribeQueryHandler() {
		return descHandler;
	}
	
	/**
	 * Return the Plan Query Handler.
	 * @return PlanQueryHandler
	 */
	public static QueryHandler getPlanQueryHandler() {
		return planHandler;
	}
	
	/**
	 * Return the DDL Query Handler.
	 * @return DDLQueryHandler
	 */
	public static QueryHandler getDDLQueryHandler() {
		return ddlHandler;
	}
    
    public static QueryHandler getTxnQueryHandler() {
        return txnQueryHandler;
    }
	
	/**
	 * Return the pagination clause for any select query.
	 * @return String
	 */
	public String getPaginationClause() {
		return PAGINATION_CLAUSE;
	}
	
	/**
	 * Return the "desc" keyword.
	 * @return String
	 */
	public String getDescribeClause() {
		return DESCRIBE_KEYWORD;
	}
	
	/**
	 * Return the describe table query.
	 * @return String
	 */
	public String getDescribeTableQuery() {
		return DESCRIBE_TABLE_QUERY;
	}
	
	/**
	 * Return the all objects query.
	 * @return String
	 */
	public String getAllObjectsQuery() {
		return OBJECTS_QUERY;
	}
	
	/**
	 * Get the DDL Table query.
	 * @return String
	 */
	public String getDDLTableQuery() {
		return TABLE_DDL_QUERY;
	}
	
	/**
	 * API to execute the sql query and fetch the result (if any).
	 * @param query
	 */
	public abstract TableData query(ConnectionCache cache, Query query) throws SQLException;
}
