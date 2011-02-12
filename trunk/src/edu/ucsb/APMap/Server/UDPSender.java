package edu.ucsb.APMap.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender implements Runnable{
	private static final int SERVERPORT = 1234;
	static byte [] SERVERADDR = new byte[] {(byte)127,(byte)0,(byte)0,(byte)1};
	byte[] buf;
	
	public UDPSender(byte[] content){
		buf = content;
	}
	
	public UDPSender(String content){
		buf = content.getBytes();
	}
	
	@Override
	public void run() {
		try {
			// Retrieve the ServerIP
			InetAddress serverAddr = InetAddress.getByAddress(SERVERADDR);
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buf, buf.length,	serverAddr, SERVERPORT);
			
			/* Send out the packet */
			socket.send(packet);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
