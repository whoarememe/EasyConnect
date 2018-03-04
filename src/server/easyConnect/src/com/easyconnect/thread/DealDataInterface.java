package com.easyconnect.thread;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public interface DealDataInterface {
	public String dealData(String msg, DatagramChannel dc, InetSocketAddress addr);
}
