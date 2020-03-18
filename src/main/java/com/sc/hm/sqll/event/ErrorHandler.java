package com.sc.hm.sqll.event;

import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ErrorHandler {

	private JLabel label;
	
	public ErrorHandler() {
		label = new JLabel("");
		label.setBorder(new EmptyBorder(5, 5, 5, 5));
	}
	
	/**
	 * Show the error details on the error panel.
	 * @param th
	 */
	public void showError(Throwable th) {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(bOut);
		th.printStackTrace(ps);
		ps.close();
		
		String s = "<html>" + new String(bOut.toByteArray()).
				replaceAll("\n", "<br>").
				replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;") + "</html>";
		label.setText(s);
		
		JScrollPane errorPane = new JScrollPane(label);
		errorPane.setSize(new Dimension(500, 300));
		errorPane.setPreferredSize(new Dimension(500, 300));
		JOptionPane.showMessageDialog(null, errorPane, "Error Message", JOptionPane.PLAIN_MESSAGE);
	}
}
