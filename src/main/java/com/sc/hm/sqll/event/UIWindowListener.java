package com.sc.hm.sqll.event;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import com.sc.hm.sqll.config.ConnectionCache;
import com.sc.hm.sqll.config.ConnectionRepos;

public class UIWindowListener extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent event) {
		// Close all existing connection(s).
		
		for (Iterator<Map.Entry<String, ConnectionCache>> itr = ConnectionRepos.getInstance().all(); itr.hasNext(); ) {
			Map.Entry<String, ConnectionCache> me = itr.next();
			ConnectionCache cache = me.getValue();
			Connection connection = cache.getNoCheck();
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e) {
					// Do Nothing.
				}
			}
		}
	}
}
