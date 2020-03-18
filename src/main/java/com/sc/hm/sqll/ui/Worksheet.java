package com.sc.hm.sqll.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.table.JTableHeader;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.config.ConnectionRepos;
import com.sc.hm.sqll.config.WorksheetConfig;
import com.sc.hm.sqll.model.TableData;

public class Worksheet extends JPanel {

	private final int spacing = 10;
	
	private final WorksheetConfig config;
	private final ConnectionCache cache;
	private final int width;
	private final int height;
	private final int splitHeightTop;
	private final int splitHeightBottom;
	
	private JScrollPane textPanel = null;
	private JTextPane textPane = null;
	private FilterPanel filterPanel = null;
	private JScrollPane resultPanel = null;
	private JTable resultTable = null;
	private JTextArea planLabel = null;
	
	private JLabel statistics = null;
	
	private boolean showingPlan = false;
	
	public Worksheet(WorksheetConfig config, int width, int height) {
		this.config = config;
		this.cache = new ConnectionCache(config.getConfig());
		this.width = width;
		this.height = height;
		this.splitHeightTop = height * 35 / 100;
		this.splitHeightBottom = height * 55 / 100;
		
		// Add the connection info to the centralized cache.
		ConnectionRepos.getInstance().addConnectionCache(config.getContext(), cache);
		
		init();
		initSplitPane();
	}

	/**
	 * Initialize this the worksheet panel.
	 */
	private void init() {
		setLayout(null);
		setBounds(0, 0, width - 3 * spacing, height - 10 * spacing);
	}

	/**
	 * Initialize the split pane.
	 * Left part will display the set of db connections, while
	 * the right pane will have the sql worksheet. 
	 */
	private void initSplitPane() {
		int leftPadding = spacing / 2;
		int componentWidth = width - 4 * spacing - leftPadding;
		
		// Initialize the the text pane.
		// This is the place where user can type the SQL query for execution.
		textPane = new JTextPane();
		textPane.setBounds(leftPadding, 0, componentWidth, splitHeightTop);
		/*textPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder()
				, BorderFactory.createLoweredBevelBorder()));*/
		textPane.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		textPanel = new JScrollPane(textPane);
		textPanel.setBounds(leftPadding, 0, componentWidth, splitHeightTop);
		textPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder()
				, BorderFactory.createLoweredBevelBorder()));
		
		// Initialize the filter panel.
		// It will have the filter box and/or the navigation options.
		filterPanel = new FilterPanel(this, componentWidth, 4 * spacing);
		filterPanel.setBounds(leftPadding, splitHeightTop + spacing, componentWidth, 4 * spacing);
		filterPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Initialize the Result Table.
		// This will hold the result (output) of the SQL Query.
		resultTable = new JTable();
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        resultTable.setFont(new Font("Verdana", Font.PLAIN, 12));
        resultTable.setBounds(0, 4 * spacing, componentWidth, splitHeightBottom - 9 * spacing);
        //resultTable.getTableHeader().setDefaultRenderer(new ResultTableHeaderRenderer());
        
        JTableHeader header = resultTable.getTableHeader();
        header.setForeground(Color.BLACK);
        header.setBackground(Color.LIGHT_GRAY);
        /*header.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 0, 0, Color.WHITE),
                new MatteBorder(0, 0, 1, 1, Color.GRAY)
        ));*/
                        
        // Initialize the Scroll Pane.
        // This in turn holds the above result table with additional scrollbar.
        resultPanel = new JScrollPane(resultTable);
		resultPanel.setBounds(leftPadding, splitHeightTop + 6 * spacing, componentWidth, splitHeightBottom - 9 * spacing);
		resultPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createRaisedBevelBorder()
				, BorderFactory.createLoweredBevelBorder()));
		
		planLabel = new JTextArea();
		planLabel.setBounds(0, 4 * spacing, componentWidth, splitHeightBottom - 9 * spacing);
		planLabel.setEditable(false);
				
		// Initialize the JLabel.
		// This will display the query time and other statistics.
		statistics = new JLabel("");
		statistics.setBounds(leftPadding, (splitHeightTop + 6 * spacing) + (splitHeightBottom - 10 * spacing) + spacing, componentWidth, 3 * spacing);
		statistics.setFont(new Font("Verdana", Font.PLAIN, 11));
		
		add(textPanel);
		add(filterPanel);
		add(resultPanel);
		add(statistics);
	}

	/**
	 * Return the worksheet configuration object.
	 * @return WorksheetConfig
	 */
	public WorksheetConfig getConfig() {
		return config;
	}

	/**
	 * Return the connection cache associated with this worksheet.
	 * @return ConnectionCache
	 */
	public ConnectionCache getCache() {
		return cache;
	}
	
	/**
	 * Get the selected text from the text pane (if any).
	 * @return String
	 */
	public String getSelectedText() {
		return textPane.getSelectedText();
	}
	
	/**
	 * Return the filter text.
	 * @return String
	 */
	public String getFilterText() {
		return filterPanel.getFilterText();
	}
	
	/**
	 * Return the rownum count.
	 * @return String
	 */
	public String getRownumText() {
		return filterPanel.getRownumText();
	}

	/**
	 * Return the table model object associated with this worksheet.
	 * @return QueryResultTableModel
	 */
	public JTable getResultTable() {
		return resultTable;
	}
	
	/**
	 * Return the label for printing the query statistics.
	 * @return JLabel
	 */
	public JLabel getStatLabel() {
		return statistics;
	}
	
	/**
	 * Show the execution plan.
	 * @param data
	 */
	public void showPlan(TableData data) {
		planLabel.setText(data.getExecutionPlan());
		resultPanel.setViewportView(planLabel);
		showingPlan = true;
	}
	
	/**
	 * Show the DDL.
	 * @param data
	 */
	public void showDDL(TableData data) {
		List<Object[]> list = data.getRows();
		Object[] row = list.get(0);
		
		textPane.setText((String)row[1]);
	}
	
	/**
	 * Show table (in case it was the plan earlier).
	 */
	public void showTable() {
		resultPanel.setViewportView(resultTable);
	}

	/**
	 * Check to see if the current result panel is being used to
	 * display query execution plan or not.
	 * @return boolean
	 */
	public boolean isShowingPlan() {
		return showingPlan;
	}
}
