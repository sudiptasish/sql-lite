package com.sc.hm.sqll.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlanOptionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		SharedObject.get().put(SharedObject.PLAN_TYPE_KEY, e.getActionCommand());
	}

}
