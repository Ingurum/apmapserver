package edu.ucsb.APMap.Server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

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
				new Thread(new TcpHandler(socket)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public class TcpHandler implements Runnable {
		private Socket socket;
		
		private TcpHandler(Socket socket) {
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
					
					if(buf[0]=='R' && buf[1]=='E' && buf[2]=='Q'){
						DBOp dbop = new DBOp("localhost", "3306", "androidwifi", "android", "cs284winter");
						String apInfo = dbop.getAPInfos();
						String apContent = "a:" + apInfo;
						outputStream.write(apContent.getBytes());
					}
					if(buf[0]=='T' && buf[1]=='R' && buf[2]=='A' && buf[3]==':' && i>9){
						String fileName = "ap-" + (new Date()).toString();
						FileOutputStream fileOutputStream = new FileOutputStream(fileName);
						int fileSize = buf[4];
						fileSize = fileSize << 8 + buf[5];
						fileSize = fileSize << 8 + buf[6];
						fileSize = fileSize << 8 + buf[7];
						System.out.println("fileSize: " + fileSize);
						fileOutputStream.write(buf, 9, i-9);
						int byteLeft = fileSize - i+9;
						while(byteLeft>0){
							if ((i = inputStream.read(buf)) != -1) {
								if (i > byteLeft)
									i = byteLeft;
								fileOutputStream.write(buf, 0, i);
							} else {
								break;
							}
							
							byteLeft -= i;
						}
						System.out.println("file receive Done!");
						fileOutputStream.close();
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
