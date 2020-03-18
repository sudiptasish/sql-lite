package com.sc.hm.sqll.ui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

public class ResultTableHeaderRenderer extends JLabel implements TableCellRenderer {
	
	public ResultTableHeaderRenderer() {
		setBackground(Color.GRAY);
        setForeground(Color.BLUE);
        setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 0, 0, Color.WHITE),
                new MatteBorder(0, 0, 1, 1, Color.GRAY)
        ));
    }

	@Override
	public Component getTableCellRendererComponent(JTable table
			, Object value
			, boolean isSelected
			, boolean hasFocus
			, int row
			, int column) {
		
		setText(value != null ? value.toString() : "");
        return this;
	}

}
