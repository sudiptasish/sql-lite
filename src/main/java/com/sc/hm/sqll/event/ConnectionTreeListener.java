package com.sc.hm.sqll.event;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.sc.hm.sqll.ui.ConnectionPanel;
import com.sc.hm.sqll.ui.UI;

public class ConnectionTreeListener extends MouseAdapter {
	
	private final Font font = new Font("Verdana", Font.PLAIN, 12);

	private final NodeMenuSelectionListener menuSelector;
	private final JPopupMenu appMenu = new JPopupMenu();
	private final JPopupMenu ddlMenu = new JPopupMenu();
    
	public ConnectionTreeListener(UI ui, ConnectionPanel connectionPanel) {
		this.menuSelector = new NodeMenuSelectionListener(ui, connectionPanel);
		initMenuSelection();
	}
	
	/**
	 * Initialize the menu selection.
	 */
	private void initMenuSelection() {
		// Open a new worksheet menu.
		JMenuItem openMenuItem = new JMenuItem("Open Worksheet");
		openMenuItem.setActionCommand("O");
		openMenuItem.addActionListener(menuSelector);
		
		// View existing connection details menu.
		JMenuItem viewMenuItem = new JMenuItem("View Configuration");
		viewMenuItem.setActionCommand("V");
		viewMenuItem.addActionListener(menuSelector);
		
		// Delete existing connection menu.
		JMenuItem deleteMenuItem = new JMenuItem("Delete Configuration");
		deleteMenuItem.setActionCommand("D");
		deleteMenuItem.addActionListener(menuSelector);
		
		// Show the schema objects.
		JMenuItem objectsMenuItem = new JMenuItem("Show Objects");
		objectsMenuItem.setActionCommand("S");
		objectsMenuItem.addActionListener(menuSelector);
		
		appMenu.add(openMenuItem);
		appMenu.add(new JSeparator());
		appMenu.add(viewMenuItem);
		appMenu.add(new JSeparator());
		appMenu.add(deleteMenuItem);
		appMenu.add(new JSeparator());
		appMenu.add(objectsMenuItem);
		
		// Prepare the ddl popup menu.
		JMenuItem ddlMenuItem = new JMenuItem("Show Defitnion");
		ddlMenuItem.setActionCommand("S");
		ddlMenuItem.addActionListener(menuSelector);
		
		ddlMenu.add(ddlMenuItem);
	}

	public void mouseClicked(MouseEvent me) {
		if (me.getButton() != MouseEvent.BUTTON3) {
        	return;
        }
		JTree tree = (JTree)me.getSource();
		
        if (tree.getSelectionPath() != null) {
            final DefaultMutableTreeNode connNode = (DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent();
            menuSelector.setCurrentSelection(connNode);
			
            if (connNode.getLevel() == 1) {
            	appMenu.show(tree, me.getX(), me.getY());
            }
            else if (connNode.getLevel() == 3) {
            	ddlMenu.show(tree, me.getX(), me.getY());
            }
        }
    }
}
