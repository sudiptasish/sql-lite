package com.sc.hm.sqll.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class QueryResultTableModel extends AbstractTableModel {
	
	private String label = "";
    
	protected Class[] classes = null;
    protected String[] columns = null;
    protected List<Object[]> rows = null;
    
    public QueryResultTableModel(String label) {
        this.label = label;
    }
    
    /**
     * Return the label.
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the new table data.
     * This API will trigger a change in table state event, which will result
     * the entire table to be repainted.
     * 
     * @param values
     */
    public void setTableModelData(TableData data) {
    	this.columns = data.getColumns();
    	this.classes = data.getClasses();
    	List<Object[]> rows = data.getRows();
		
		if (this.rows == null) {
        	this.rows = rows;
        }
        else {
        	this.rows.addAll(rows);
        }
        //fireTableDataChanged();
    }

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}
    
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}
    
    /* (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
     */
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return classes[columnIndex];
    }
}
