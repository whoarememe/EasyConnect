package com.easyconnect.thread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HeartListener implements ServletContextListener {
	private HeartThread t;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (t != null && t.isInterrupted()) {
			t.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		if (t == null) {

		}
		
	}

}
