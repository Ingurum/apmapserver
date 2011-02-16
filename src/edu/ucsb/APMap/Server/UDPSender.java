package edu.ucsb.APMap.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender implements Runnable{
	byte[] buf;
	static int CLIENTPORT = 4234;
	InetAddress clientIp;
	
	public UDPSender(InetAddress clientIp, byte[] buf){
		this.buf = buf;
		this.clientIp = clientIp;
	}
	
	public UDPSender(InetAddress clientIp, String str){
		this.buf = str.getBytes();
		this.clientIp = clientIp;
	}
	
	@Override
	public void run() {
		try {
			// Retrieve the ServerIP
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buf, buf.length,	clientIp, CLIENTPORT);
			
			/* Send out the packet */
			socket.send(packet);
			System.out.println("send " + buf + " done");
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
