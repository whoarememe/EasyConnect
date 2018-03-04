package com.easyconnect.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public class Udp {
	private DatagramSocket udpSocket;
	private int port;
	private DatagramPacket packet;
	private DatagramChannel channel;
	private Selector selector;
	private byte b[];
	
	public Udp(int port) {
		System.out.println("udp init");
		this.port = port;
		try {
			this.udpSocket = new DatagramSocket(this.port);
			
			
			channel = DatagramChannel.open();
			channel.configureBlocking(false);
			channel.socket().bind(new InetSocketAddress(port));
			
			selector = Selector.open();
			channel.register(selector, SelectionKey.OP_READ);
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void sendTo(InetAddress dstAddr, int dstPort, String msg) {
		System.out.println("send msg string init");
		this.b = msg.getBytes();
		this.packet = new DatagramPacket(b, b.length, dstAddr, dstPort);
		
		if (this.udpSocket == null) {
			try {
				this.udpSocket = new DatagramSocket(this.port);
				this.udpSocket.send(this.packet);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.udpSocket.send(this.packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void sendTo(InetAddress dstAddr, int dstPort, byte b[]) {
		System.out.println("send msg bytes init");
		this.packet = new DatagramPacket(b, b.length, dstAddr, dstPort);
		
		if (this.udpSocket == null) {
			try {
				this.udpSocket = new DatagramSocket(this.port);
				this.udpSocket.send(this.packet);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				this.udpSocket.send(this.packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public DatagramPacket recvfrom(int length) {
		System.out.println("recv msg pkt init");
		this.b = new byte[length];
		this.packet = new DatagramPacket(this.b, this.b.length);
		
		if (this.udpSocket == null) {
			try {
				this.udpSocket = new DatagramSocket(this.port);
				this.udpSocket.receive(this.packet);
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				this.udpSocket.receive(this.packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return this.packet;
	}
	
	public InetAddress getAddr() {
		return this.packet.getAddress();
	}
	
	public int getPort() {
		return this.packet.getPort();
	}
}
