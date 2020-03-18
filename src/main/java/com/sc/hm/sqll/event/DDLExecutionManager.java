package com.sc.hm.sqll.event;

import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.query.Query;
import com.sc.hm.sqll.query.QueryHandler;
import com.sc.hm.sqll.ui.Worksheet;

public class DDLExecutionManager extends ExecutionManager {
	
	DDLExecutionManager() {
		super();
	}

	@Override
	public void prepare(Worksheet ws, String query, Object callback) {
		try {
			// First set the empty table Model.
			ws.getResultTable().setModel(new DefaultTableModel());
			
			// Now try fetching the records from the underlying DB.			
			Query queryObj = new Query(getQueryHandler().getDDLTableQuery(), 10000);
			queryObj.addParameter(String.class, "TABLE");
			queryObj.addParameter(String.class, query);		// Here query is just the table name.
			queryObj.addParameter(String.class, ws.getConfig().getConfig().getUser().toUpperCase());
			
			TableData data = getQueryHandler().query(ws.getCache(), queryObj);
			
			// Refresh the table model to display the new set of data.
			updateResult(ws, data);
			
			// Update the execution statistics.
			displayStats(ws, data);
		}
		catch (SQLException e) {
			getErrorHandler().showError(e);
		}
	}
	
	/**
	 * Update the display panel of the worksheet with the query execution plan.
	 * 
	 * @param ws	Worksheet object
	 * @param data	Output of the current query.
	 */
	public void updateResult(Worksheet ws, TableData data) {
		long startTime = System.currentTimeMillis();
		
		// Display the execution plan
		ws.showDDL(data);
		
		// Show the query statistics.
		data.getStatistics().setRenderTime(System.currentTimeMillis() - startTime);
	}
	
	@Override
	public QueryHandler getQueryHandler() {
		return QueryHandler.getDDLQueryHandler();
	}
}
