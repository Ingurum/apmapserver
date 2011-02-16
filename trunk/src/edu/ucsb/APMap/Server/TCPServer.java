package edu.ucsb.APMap.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import edu.ucsb.APMap.util.DBOp;


public class TCPServer implements Runnable{
	int serverPort = 8888;
	
	public void run() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(serverPort);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while (true)
		{
			try {
				System.out.println("TCP listen on port " + serverPort);
				Socket socket = serverSocket.accept();
				new Thread(new ConnectionHandler(socket)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class ConnectionHandler implements Runnable {
		private Socket socket;
		
		private ConnectionHandler(Socket socket) {
			this.socket = socket;
		}
		public void run () {
			System.out.println("accepted");
			java.io.InputStream inputStream;
			
			try {
				inputStream = (java.io.InputStream) socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				byte[] buf = new byte[1024];
				int i = 0;
				
				while((i = inputStream.read(buf)) != -1){
					String recStr = new String(buf, 0, i);
	                System.out.println(recStr);
	                
					if(recStr.startsWith("REQ")){
						DBOp dbop = new DBOp("localhost", "3306", "androidwifi", "android", "cs284winter");
						String apInfo = dbop.getAPInfos();
						String apContent = "a:" + apInfo;
						outputStream.write(apContent.getBytes());
					}
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{   
				try {   
					if(socket != null) {   
						socket.close();   
					}
				} catch (IOException e) {   
						e.printStackTrace();   
				}
			}
		}
	}
	
	public static void main(String[] args){
		new Thread(new TCPServer()).start();
	}
}
