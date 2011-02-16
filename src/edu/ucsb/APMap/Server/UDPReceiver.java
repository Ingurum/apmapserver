package edu.ucsb.APMap.Server;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import edu.ucsb.APMap.util.DBOp;

class UDPReceiver
{
	public static final String IP = "169.254.87.205";
	public static final int PORT = 1222;
	
	public static void main(String args[]) throws Exception
	  {
			InetAddress serverAddr = InetAddress.getByName(IP);
			DatagramSocket serverSocket = new DatagramSocket(PORT, serverAddr);
	        byte[] receiveData = new byte[1024];
	        while(true)
	           {
	              System.out.println("server receiver running...");
	        	  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	              serverSocket.receive(receivePacket);
	              InetAddress clientIp = receivePacket.getAddress();
	              String content = new String( receivePacket.getData());
	              System.out.println("receive " + content);
	              if(content.startsWith("REQ")){
	            	  DBOp dbop = new DBOp("localhost", "3306", "androidwifi", "android", "cs284winter");
	            	  String apInfo = dbop.getAPInfos();
	            	  String apContent = "a:" + apInfo;
	            	  UDPSender u = new UDPSender(clientIp, apContent);
	            	  u.run();
				  }
				  else if(content.startsWith("TRC")){
				  // TODO store the trace
				  }
				  else{
					  System.out.println("Unknown request type");
	              }
	           }
	  }
	
	private String getWifiNearby(String longtidude, String latitude){
		String wifiData = null;
		
		return wifiData;
	}
}
