package com.sc.hm.sqll.main;

import javax.swing.SwingUtilities;

import com.sc.hm.sqll.config.ConfigurationUtil;
import com.sc.hm.sqll.ui.UI;

public class SQLLiteMain {
	
	private static UI ui = null;

	public static void main(String[] args) {
		start();
	}

	/**
	 * Initialize and start the UI.
	 */
	private static void start() {
		ConfigurationUtil.configure();
		ui = new UI();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ui.start();
			}
		});
	}
}
