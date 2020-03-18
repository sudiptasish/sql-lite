package com.sc.hm.sqll.event;

import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.sc.hm.sqll.model.QueryResultTableModel;
import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.query.Query;
import com.sc.hm.sqll.query.QueryHandler;
import com.sc.hm.sqll.ui.Worksheet;

public class DefaultExecutionManager extends ExecutionManager {
	
	DefaultExecutionManager() {
		super();
	}

	@Override
	public void prepare(Worksheet ws, String query, Object callback) {
		try {
            if (ExecutionListener.COMMIT_COMMAND.equals(query)
                || ExecutionListener.ROLLBACK_COMMAND.equals(query)) {
                
                transactional(ws, query);
                return;
            }
            
			// First set the empty table Model.
			ws.getResultTable().setModel(new DefaultTableModel());
			
			QueryHandler handler = null;
			if (isDescribeQuery(query)) {
				handler = getDescQueryHandler();
			}
			else {
				handler = getQueryHandler();
			}
			
			// Now try fetching the records from the underlying DB.			
			TableData data = handler.query(ws.getCache(), new Query(query, getLimit(ws)));
			
			// Refresh the table model to display the new set of data.
			refreshTableModel(ws, data);
			
			// Update the execution statistics.
			displayStats(ws, data);
		}
		catch (SQLException e) {
			getErrorHandler().showError(e);
		}
	}

    public void transactional(Worksheet ws, String query) {
        try {
            QueryHandler handler = QueryHandler.getTxnQueryHandler();
            handler.query(ws.getCache(), new Query(query, 0,
                ExecutionListener.COMMIT_COMMAND.equals(query)
                    ? QueryHandler.COMMIT_QUERY
                    : QueryHandler.ROLLBACK_QUERY));
            
            
        }
		catch (SQLException e) {
			getErrorHandler().showError(e);
		}
    }
	
	/**
	 * Get the result set limit set in the worksheet.
	 * If no limit is specified, then accept the default.
	 * 
	 * @param ws
	 * @return int
	 */
	private int getLimit(Worksheet ws) {
		String limitTxt = ws.getRownumText();
		int limit = 0;
		try {
			limit = Integer.parseInt(limitTxt);
		}
		catch (NumberFormatException e) {
			limit = QueryHandler.LIMIT;
		}
		return limit;
	}
	
	/**
	 * Create a new table model object with the result set, and
	 * send it to the result table.
	 * 
	 * @param ws
	 * @param data
	 */
	public void refreshTableModel(Worksheet ws, TableData data) {
		long startTime = System.currentTimeMillis();
		
		if (ws.isShowingPlan()) {
			ws.showTable();
		}
		
		// Prepare the new table model with the db records.
		QueryResultTableModel tableModel = new QueryResultTableModel("Result");
		tableModel.setTableModelData(data);
		
		// Create and set the new sorter.
		TableRowSorter<QueryResultTableModel> sorter = new TableRowSorter<>(tableModel);
		ws.getResultTable().setRowSorter(sorter);

		// Set the cell renderer and the new table model.
		/*Class[] columnClasses = data.getClasses();
		for (int i = 0; i < columnClasses.length; i ++) {
			if (columnClasses[i] == Timestamp.class) {
				ws.getResultTable().getColumnModel().getColumn(i).setCellRenderer(new TimestampCellRenderer());
			}
		}*/
		ws.getResultTable().setModel(tableModel);
		data.getStatistics().setRenderTime(System.currentTimeMillis() - startTime);
	}
}
