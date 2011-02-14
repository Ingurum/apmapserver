package edu.ucsb.APMap.Server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSender implements Runnable{
	byte[] buf;
	static int CLIENTPORT = 1234;
	static byte [] CLIENTADDR = new byte[] {(byte)127,(byte)0,(byte)0,(byte)1};
	
	public UDPSender(byte[] buf){
		this.buf = buf;
	}
	
	public UDPSender(String str){
		this.buf = str.getBytes();
	}
	
	@Override
	public void run() {
		try {
			// Retrieve the ServerIP
			DatagramSocket socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buf, buf.length,	InetAddress.getByAddress(CLIENTADDR), CLIENTPORT);
			
			/* Send out the packet */
			socket.send(packet);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
	}
}
