package com.easyconnect.socket;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;

import com.easyconnect.thread.DealDataInterface;
import com.easyconnect.thread.DealThread;

public class NioUdp {
	private Selector selector;
	private DatagramChannel channel;
	private DatagramSocket socket;
	private ByteBuffer recvBuffer;
	private int port;
	private DealThread dl;
	
	public NioUdp(int port) {
		this.port = port;
	}
	
	public void start(DealDataInterface ddi) {
		try {
			selector = Selector.open();
			channel = DatagramChannel.open();
			channel.configureBlocking(false);
			socket = channel.socket();
			socket.bind(new InetSocketAddress(port));
			channel.register(selector, SelectionKey.OP_READ);
			

			while (true) {
				int n = selector.select();
				
				if (n == 0) continue;
				
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				

				for (SelectionKey key: readyKeys) {
					readyKeys.remove(key);
					
					if (key.isReadable()) {
						DatagramChannel datagramChannel = (DatagramChannel)key.channel();
						
						recvBuffer = ByteBuffer.allocate(1024);
						
						InetSocketAddress addr = (InetSocketAddress)datagramChannel.receive(recvBuffer);
						
						dl = new DealThread(datagramChannel, addr, new String(recvBuffer.array()), ddi);
						dl.start();
						
					}
				}
			}
			
			
		} catch (IOException e) {  
            e.printStackTrace();  
        }  
	}
	
	public void sendMsg(String msg, InetSocketAddress addr) {
		
	}
}
