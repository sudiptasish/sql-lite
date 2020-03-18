package com.sc.hm.sqll.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.sc.hm.sqll.ui.UI;
import com.sc.hm.sqll.ui.Worksheet;

public class ExecutionListener implements ActionListener {
	
	public static final String EXECUTE_COMMAND = "EXECUTE";
	public static final String COMMIT_COMMAND = "COMMIT";
	public static final String ROLLBACK_COMMAND = "ROLLBACK";
	public static final String PLAN_COMMAND = "SQL PLAN";
	
	private final UI ui;
	
	public ExecutionListener(UI ui) {
		this.ui = ui;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JButton source = (JButton)event.getSource();
		
		if (EXECUTE_COMMAND.equals(source.getText())) {
			Worksheet ws = (Worksheet)ui.getTabbedPane().getSelectedComponent();
			if (ws == null) {
				return;
			}
			String query = ws.getSelectedText();
			if (query != null && (query = query.trim()).length() > 0) {
				ExecutionManager.getDefaultManager().prepare(ws, query, null);
			}
		}
        else if (COMMIT_COMMAND.equals(source.getText())) {
            Worksheet ws = (Worksheet)ui.getTabbedPane().getSelectedComponent();
			if (ws == null) {
				return;
			}
            ExecutionManager.getDefaultManager().prepare(ws, COMMIT_COMMAND, null);
        }
        else if (ROLLBACK_COMMAND.equals(source.getText())) {
            Worksheet ws = (Worksheet)ui.getTabbedPane().getSelectedComponent();
			if (ws == null) {
				return;
			}
            ExecutionManager.getDefaultManager().prepare(ws, ROLLBACK_COMMAND, null);
        }
		else if (PLAN_COMMAND.equals(source.getText())) {
			Worksheet ws = (Worksheet)ui.getTabbedPane().getSelectedComponent();
			if (ws == null) {
				return;
			}
			String query = ws.getSelectedText();
			if (query != null && (query = query.trim()).length() > 0) {
				ExecutionManager.getPlanManager().prepare(ws, query, null);
			}
		}
	}
}
