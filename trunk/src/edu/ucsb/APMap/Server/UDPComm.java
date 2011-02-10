package edu.ucsb.APMap.Server;
import java.net.*;

class UDPComm
{
	//public static final String SERVERIP = "128.111.52.236";
	public static final String SERVERIP = "127.0.0.1";
	public static final int SERVERPORT = 0;
	
	public static void main(String args[]) throws Exception
      {
			InetAddress serverAddr = InetAddress.getByName(SERVERIP);
			DatagramSocket serverSocket = new DatagramSocket(SERVERPORT, serverAddr);
            byte[] receiveData = new byte[1024];
            while(true)
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String( receivePacket.getData());
                  System.out.println("RECEIVED: " + sentence);
               }
      }
}
