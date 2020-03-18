package com.sc.hm.sqll.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sc.hm.sqll.event.UIWindowListener;

public class UI extends JFrame {

	private static final Dimension screen_dimension = Toolkit.getDefaultToolkit().getScreenSize();
	
	private final int width = (int)screen_dimension.getWidth();
	private final int height = (int)screen_dimension.getHeight();
	private final int splitWidth = width * 18 / 100;
	
	private final int spacing = 10;
	
	private MenuPanel menu = null;
	private JTabbedPane tabbedPane = null;
	
	private ConnectionPanel connectionPanel = null;
	private ControlPanel controlPanel = null;
	private List<Worksheet> worksheets = new ArrayList<>();
	
	public UI() {
		getContentPane().setLayout(null);
		initSplitPane();
	}

	/**
	 * Initialize the split pane.
	 * Left part will display the set of db connections, while
	 * the right pane will have the sql worksheet. 
	 */
	private void initSplitPane() {
		/*try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			//SwingUtilities.updateComponentTreeUI(this);
		}
		catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}*/
		
		// Initialize the connection panel.
		// This panel will display the list of available connection details
		connectionPanel = new ConnectionPanel(this, splitWidth, height - 12 * spacing);
		
		// Top menu bar.
		menu = new MenuPanel(connectionPanel.getConfigPanel());
		
		// Initialize the control panel.
		// This panel holds the "execute", "commit", etc buttons.
		controlPanel = new ControlPanel(this, width - splitWidth - 3 * spacing, 4 * spacing, splitWidth);
		
		// The Tabbed Pane.
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(2 * spacing + splitWidth, 6 * spacing, width - splitWidth - 3 * spacing, height - 17 * spacing);
		
		getContentPane().add(connectionPanel);
		getContentPane().add(controlPanel);
		getContentPane().add(tabbedPane);
	}
	
	/**
	 * Return the tabbed pane.
	 * @return JTabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	/**
	 * Return the width the main UI.
	 * @return int
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Return the height the main UI.
	 * @return int
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Add worksheet.
	 * @param sheet
	 */
	public void addWorksheet(Worksheet sheet) {
		worksheets.add(sheet);
	}

	/**
	 * Initialize UI, set the screen size and dimension.
	 */
	public void start() {
		setTitle("SQL LiTE");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(new Dimension(width, height));
		setJMenuBar(menu);
		setLocation((int)(screen_dimension.getWidth() / 2 - getSize().width / 2)
				, (int)(screen_dimension.getHeight() / 2 - getSize().getHeight() / 2));
		addWindowListener(new UIWindowListener());
		
		setVisible(true);
	}
}
