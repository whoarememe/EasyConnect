package com.easyconnect.thread;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;


public class DealThread extends Thread {
	private String msg;
	private DatagramChannel dc;
	private InetSocketAddress addr;
	private DealDataInterface ddi;
	
	public DealThread(DatagramChannel dc, InetSocketAddress addr, String str, DealDataInterface ddi) {
		this.msg = str;
		this.dc = dc;
		this.addr = addr;
		this.ddi = ddi;
	}
	
	public void run() {	
		this.ddi.dealData(this.msg,  this.dc, this.addr);
	}
	
}
