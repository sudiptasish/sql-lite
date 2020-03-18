package com.sc.hm.sqll.event;

import java.sql.SQLException;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.sc.hm.sqll.model.TableData;
import com.sc.hm.sqll.query.Query;
import com.sc.hm.sqll.ui.Worksheet;

public class SchemaExecutionManager extends ExecutionManager {
	
	SchemaExecutionManager() {
		super();
	}

	@Override
	public void prepare(Worksheet ws, String query, Object callback) {
		try {
			// Now try fetching the records from the underlying DB.			
			TableData data = getQueryHandler().query(ws.getCache(), new Query(getQueryHandler().getAllObjectsQuery(), 5000));
			
			// Update the tree.
			updateTree((DefaultMutableTreeNode)callback, data);
			
			// Update the execution statistics.
			displayStats(ws, data);
		}
		catch (SQLException e) {
			getErrorHandler().showError(e);
		}
	}

	/**
	 * Update the connection tree with schema object details.
	 * @param connNode
	 * @param data
	 */
	private void updateTree(DefaultMutableTreeNode connNode, TableData data) {
		String currentType = "";
		DefaultMutableTreeNode currentParent = null;
		
		List<Object[]> list = data.getRows();
		for (int i = 0; i < list.size(); i ++) {
			Object[] row = list.get(i);
			if (!currentType.equals(row[1])) {
				currentType = row[1].toString();
				connNode.add(currentParent = new DefaultMutableTreeNode(currentType));
			}
			currentParent.add(new DefaultMutableTreeNode(row[2]));
		}
	}
}
