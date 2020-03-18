package com.sc.hm.sqll.ui;

import java.awt.Font;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import com.sc.hm.sqll.config.ConfigurationUtil;
import com.sc.hm.sqll.config.ConnectionConfig;
import com.sc.hm.sqll.event.ConnectionTreeListener;
import com.sc.hm.sqll.model.ConnectionTreeModel;

public class ConnectionPanel extends JPanel {

	private final int spacing = 10;
	
	private final UI ui;
	private final int width;
	private final int height;
	
	private JTree connTree;
	private ConfigurationPanel configPanel;
	
	public ConnectionPanel(UI ui, int width, int height) {
		this.ui = ui;
		this.width = width;
		this.height = height;
		
		init();
		initConnectionTree();
	}

	/**
	 * Initialize the connection panel.
	 */
	private void init() {
		setLayout(null);
		setBounds(spacing, spacing, width, height);
		setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder()
				, BorderFactory.createTitledBorder("Connection")));
		setFont(new Font("Verdana", Font.PLAIN, 13));
	}

	/**
	 * Initialize the connection tree.
	 */
	private void initConnectionTree() {
		// Create the root node for all connection.
		// Any new connection node will be a child of this.
		MutableTreeNode rootNode = new DefaultMutableTreeNode("All Connections");
		ConnectionTreeModel treeModel = new ConnectionTreeModel(rootNode);
		
		// Start preparing the child nodes.
		Iterator<ConnectionConfig> itr = ConfigurationUtil.getAllConfigs();
		int index = 0;
		while (itr.hasNext()) {
			ConnectionConfig config = itr.next();
			treeModel.insertNodeInto(new DefaultMutableTreeNode(config), rootNode, index ++);
		}
		
		// Construct the final Tree.
		connTree = new JTree(treeModel);
		connTree.setFont(new Font("Verdana", Font.PLAIN, 12));
		connTree.setBorder(BorderFactory.createEmptyBorder(spacing, spacing, spacing, spacing));
		
		connTree.addMouseListener(new ConnectionTreeListener(ui, this));
		connTree.setModel(treeModel);
		
		JScrollPane scrollPane = new JScrollPane(connTree);
		scrollPane.setBounds(2 * spacing, 5 * spacing, width - 4 * spacing, height - 8 * spacing);
		
		// Instantiate the configuration panel.
		// This panel will be displayed whenever client wants to configure a new database.
		configPanel = new ConfigurationPanel(this, 600, 420);
        
        add(scrollPane);
	}
	
	/**
	 * Return the configuration panel.
	 * @return ConfigurationPanel
	 */
	public ConfigurationPanel getConfigPanel() {
		return configPanel;
	}
	
	/**
	 * Return the connection tree hierarchy.
	 * @return JTree
	 */
	public JTree getConnectionTree() {
		return connTree;
	}
	
	/**
	 * Add the new database connection detail and update the tree.
	 * @param config
	 */
	public void addNewConfigAndUpdateTree(ConnectionConfig config) {
		DefaultTreeModel treeModel = (DefaultTreeModel)connTree.getModel();
		treeModel.insertNodeInto(new DefaultMutableTreeNode(config)
			, (DefaultMutableTreeNode)treeModel.getRoot()
			, treeModel.getChildCount(treeModel.getRoot()));
	}
	
	/**
	 * Remove an existing database connection detail and update the tree.
	 * @param connNode
	 */
	public void removeConfigAndUpdateTree(DefaultMutableTreeNode connNode) {
		DefaultTreeModel treeModel = (DefaultTreeModel)connTree.getModel();		
		treeModel.removeNodeFromParent(connNode);
	}
}
