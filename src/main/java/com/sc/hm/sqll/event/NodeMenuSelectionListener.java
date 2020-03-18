package com.sc.hm.sqll.event;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.sc.hm.sqll.config.ConnectionConfig;
import com.sc.hm.sqll.config.WorksheetConfig;
import com.sc.hm.sqll.ui.ConfigurationPanel;
import com.sc.hm.sqll.ui.ConnectionPanel;
import com.sc.hm.sqll.ui.UI;
import com.sc.hm.sqll.ui.Worksheet;

public class NodeMenuSelectionListener implements ActionListener {
	
	private final UI ui;
	private final ConnectionPanel connectionPanel;
	
	private DefaultMutableTreeNode selectedNode;

	public NodeMenuSelectionListener(UI ui, ConnectionPanel connectionPanel) {
		this.ui = ui;
		this.connectionPanel = connectionPanel;
	}

	/**
	 * Set the current selection node.
	 * @param selectedNode
	 */
	public void setCurrentSelection(DefaultMutableTreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object userObj = selectedNode.getUserObject();
		
		if (selectedNode.getLevel() == 1) {
			// Database Connection Name is the Level 1.
			// Extracts the connection configuration associated with this node.
			ConnectionConfig config = (ConnectionConfig)userObj;
			if ("O".equals(e.getActionCommand())) {
				openWorksheet(new WorksheetConfig(config));
			}
			else if ("V".equals(e.getActionCommand())) {
				viewConnection(config);
			}
			else if ("D".equals(e.getActionCommand())) {
				deleteConnection(selectedNode);
			}
			else if ("S".equals(e.getActionCommand())) {
				showObjects(new WorksheetConfig(config), selectedNode);
			}
		}
		else if (selectedNode.getLevel() == 3) {
			// Database Schema Object Node is Level 3.
			// Get the parent node, which will tell the object type.
			DefaultMutableTreeNode parent = (DefaultMutableTreeNode)selectedNode.getParent();
			String type = (String)parent.getUserObject();
			String name = (String)userObj;
			
			ConnectionConfig config = (ConnectionConfig)((DefaultMutableTreeNode)parent.getParent()).getUserObject();
			
			if (type.equals("TABLE")) {
				showTableMetadata(new WorksheetConfig(config), name);
			}
		}
	}
	
	/**
	 * Add a new worksheet.
	 * @param wsConfig
	 */
	public void openWorksheet(WorksheetConfig wsConfig) {
		Worksheet worksheet = new Worksheet(wsConfig, ui.getWidth() - ui.getWidth() * 18 / 100, ui.getHeight() - 10 * 10);
		ui.addWorksheet(worksheet);
		
		Component component = ui.getTabbedPane().add(wsConfig.getContext(), worksheet);
		ui.getTabbedPane().setSelectedComponent(component);
	}
	
	/**
	 * View existing connection configuration.
	 * @param config
	 */
	public void viewConnection(ConnectionConfig config) {
		ConfigurationPanel configPanel = connectionPanel.getConfigPanel();
		configPanel.populate(config);
		configPanel.disableComponents();
		
		JOptionPane.showOptionDialog(null
    			, configPanel
    			, "Database Configuration"
    			, JOptionPane.YES_OPTION
    			, JOptionPane.PLAIN_MESSAGE
    			, null
    			, new Object[] {"Close"}, null);
	}
	
	/**
	 * Delete existing connection.
	 * @param connNode
	 */
	public void deleteConnection(DefaultMutableTreeNode connNode) {
		connectionPanel.removeConfigAndUpdateTree(connNode);
	}

	/**
	 * Show the schema objects.
	 * @param connNode
	 */
	public void showObjects(WorksheetConfig wsConfig, DefaultMutableTreeNode connNode) {
		Worksheet worksheet = new Worksheet(wsConfig, ui.getWidth() - ui.getWidth() * 18 / 100, ui.getHeight() - 10 * 10);
		ui.addWorksheet(worksheet);
		
		ExecutionManager.getSchemaManager().prepare(worksheet, null, connNode);		
		ui.getTabbedPane().add(wsConfig.getContext(), worksheet);
	}
	
	/**
	 * Show the table metadata.
	 * @param tableName
	 */
	public void showTableMetadata(WorksheetConfig wsConfig, String tableName) {
		Worksheet worksheet = new Worksheet(wsConfig, ui.getWidth() - ui.getWidth() * 18 / 100, ui.getHeight() - 10 * 10);
		ui.addWorksheet(worksheet);
		
		ExecutionManager.getDDLManager().prepare(worksheet, tableName, wsConfig.getConfig());	
		Component component = ui.getTabbedPane().add(wsConfig.getContext(), worksheet);
		ui.getTabbedPane().setSelectedComponent(component);
	}
}
