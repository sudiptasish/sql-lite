package com.sc.hm.sqll.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.config.ConnectionRepos;

public class MenuPanel extends JMenuBar {
	
	private final ConfigurationPanel configPanel;

	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu toolsMenu;
	private JMenu windowMenu;
	private JMenu helpMenu;
	
    private JMenuItem newItem;
    private JMenuItem exitItem;
    private JMenuItem defaultLAFItem;
    private JMenuItem winLAFItem;
    private JMenuItem motifLAFItem;
    
    public MenuPanel(ConfigurationPanel configPanel) {
    	this.configPanel = configPanel;
    	init();
    	addListener();
    	addMenuItem();
    }

	/**
     * Initialize the menubar and menu items.
     */
	private void init() {
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		editMenu = new JMenu("Edit");
		editMenu.setMnemonic(KeyEvent.VK_E);
		
		toolsMenu = new JMenu("Tools");
		toolsMenu.setMnemonic(KeyEvent.VK_T);
		
		windowMenu = new JMenu("Window");
		windowMenu.setMnemonic(KeyEvent.VK_W);
		
		helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		newItem = new JMenuItem("Create New Connection");
		exitItem = new JMenuItem("Exit");
		fileMenu.add(newItem);
		fileMenu.add(exitItem);
		
		defaultLAFItem = new JMenuItem("Default Look And Feel");
		winLAFItem = new JMenuItem("Windows Classic Look And Feel");
		motifLAFItem = new JMenuItem("Motif Look And Feel");
		windowMenu.add(defaultLAFItem);
		windowMenu.add(winLAFItem);
		windowMenu.add(motifLAFItem);
	}

    /**
     * Add the necessary listeners.
     */
	private void addListener() {
		// Add Listener for "new Connection" Menu Item.
		newItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	        	configPanel.reset();
	        	configPanel.enableComponents();
	        	JOptionPane.showOptionDialog(null
	        			, configPanel
	        			, "Database Configuration"
	        			, JOptionPane.YES_OPTION
	        			, JOptionPane.PLAIN_MESSAGE
	        			, null
	        			, new Object[] {"Close"}, null);
	        }
		});
		
		// Action Listener for "Exit" Menu Item.
		exitItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	        	for (Iterator<Map.Entry<String, ConnectionCache>> itr = ConnectionRepos.getInstance().all(); itr.hasNext(); ) {
	    			Map.Entry<String, ConnectionCache> me = itr.next();
	    			ConnectionCache cache = me.getValue();
	    			Connection connection = cache.getNoCheck();
	    			if (connection != null) {
	    				try {
	    					connection.close();
	    				}
	    				catch (SQLException e) {
	    					// Do Nothing.
	    				}
	    			}
	    		}
	            System.exit(0);
	        }
	    });
		
		// Action Listener for default look and feel.
		defaultLAFItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	        	try {
					UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
					SwingUtilities.updateComponentTreeUI(getParent());
				}
				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
	        }
	    });
		
		// Action Listener for windows look and feel.
		winLAFItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	        	try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					SwingUtilities.updateComponentTreeUI(getParent());
				}
				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
	        }
	    });
		
		// Action Listener for motif look and feel.
		motifLAFItem.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent ev) {
	        	try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
					SwingUtilities.updateComponentTreeUI(getParent());
				}
				catch (ClassNotFoundException | InstantiationException
						| IllegalAccessException | UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
	        }
	    });
	}

	/**
	 * Add the menu items.
	 */
    private void addMenuItem() {
    	add(fileMenu);
    	add(editMenu);
    	add(toolsMenu);
    	add(windowMenu);
    	add(helpMenu);
	}
}
