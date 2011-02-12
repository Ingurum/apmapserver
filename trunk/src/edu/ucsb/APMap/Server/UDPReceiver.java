package edu.ucsb.APMap.Server;
import java.net.*;

class UDPReceiver
{
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
	              String content = new String( receivePacket.getData());
	              if(content.startsWith("REQ")){
				  // TODO send back the AP info
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
