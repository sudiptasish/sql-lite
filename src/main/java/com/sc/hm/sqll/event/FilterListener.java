package com.sc.hm.sqll.event;

import java.util.regex.PatternSyntaxException;

import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import javax.swing.text.BadLocationException;

import com.sc.hm.sqll.model.QueryResultTableModel;
import com.sc.hm.sqll.ui.Worksheet;

public class FilterListener implements DocumentListener {

	private final Worksheet worksheet;
	
	public FilterListener(Worksheet worksheet) {
		this.worksheet = worksheet;
	}
	
	@Override
	public void insertUpdate(DocumentEvent event) {
		filter(worksheet.getFilterText());
	}

	@Override
	public void removeUpdate(DocumentEvent event) {
		filter(worksheet.getFilterText());
	}

	@Override
	public void changedUpdate(DocumentEvent event) {
		filter(worksheet.getFilterText());
	}
	 
    /** 
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void filter(String text) {
        try {
        	RowFilter<QueryResultTableModel, Object> rf = RowFilter.regexFilter(text, 0);
        	TableRowSorter<QueryResultTableModel> sorter = (TableRowSorter<QueryResultTableModel>)worksheet.getResultTable().getRowSorter();
        	if (sorter != null) {
        		sorter.setRowFilter(rf);
        	}
        }
        catch (PatternSyntaxException e) {
            e.printStackTrace();
        }
    }
}
