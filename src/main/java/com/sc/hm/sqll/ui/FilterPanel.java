package com.sc.hm.sqll.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.sc.hm.sqll.event.FilterListener;
import com.sc.hm.sqll.query.QueryHandler;

public class FilterPanel extends JPanel {

	private final int spacing = 10;
	
	private final Worksheet worksheet;
	private final int width;
	private final int height;
	
	private JLabel filterLabel = null;
	private JTextField filterText = null;
	
	private JLabel rownumLabel = null;
	private JTextField rownumText = null;
	
	public FilterPanel(Worksheet worksheet, int width, int height) {
		this.worksheet = worksheet;
		this.width = width;
		this.height = height;
		
		init();
		initButtons();
		addButtons();
	}

	/**
	 * Filter panel initialization.
	 */
	private void init() {
		setLayout(null);
	}

	/**
	 * Initialize the buttons.
	 */
	private void initButtons() {
		filterLabel = new JLabel("Filter Text:");
		filterLabel.setBounds(spacing, spacing, 80, height - 2 * spacing);
		
		filterText = new JTextField();
		filterText.setBounds(spacing + 80 + spacing, spacing, 200, height - 2 * spacing);
		filterText.getDocument().addDocumentListener(new FilterListener(worksheet));
		
		rownumText = new JTextField(String.valueOf(QueryHandler.LIMIT));
		rownumText.setBounds(width - spacing - 50 - spacing, spacing, 50, height - 2 * spacing);
		
		rownumLabel = new JLabel("Max Record Count:");
		rownumLabel.setBounds(width - spacing - 50 - spacing - 120 - spacing, spacing, 120, height - 2 * spacing);
	}

	private void addButtons() {
		add(filterLabel);
		add(filterText);
		add(rownumLabel);
		add(rownumText);
	}
	
	/**
	 * Return the filter text.
	 * @return String
	 */
	public String getFilterText() {
		return filterText.getText();
	}
	
	/**
	 * Return the rownum text.
	 * @return String
	 */
	public String getRownumText() {
		return rownumText.getText();
	}
}
