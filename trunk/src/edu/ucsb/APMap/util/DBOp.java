package edu.ucsb.APMap.util;

import java.sql.*;

public class DBOp {
	
	Connection mysqlCon;
	
	public DBOp(String dbServer, String dbPort, String dbName, String dbUser, String dbPassword) {
		String driveName = "com.mysql.jdbc.Driver";
		String databaseURL = "jdbc:mysql://" + dbServer + ":" + dbPort + "/" + dbName;
		try{
			Class.forName(driveName);
			System.out.println("Database driver loaded");
		}
		catch(java.lang.ClassNotFoundException e){
			System.out.println("Database driver load failed!!!");
			System.out.println(e.getMessage());
			return;
		}
		try{
			this.mysqlCon = DriverManager.getConnection(databaseURL, dbUser, dbPassword);
			
			System.out.println("Database connected");
		}
		catch(SQLException e){
			System.out.println("Database connect failed");
			System.out.println("SQLException: " + e.getMessage());
			return;
		}
	}
	
	public boolean insertAPInfo(String bssid, String ssid, String capabilities, double frequency, double longtitude, double latitude){
		try {
			Statement stmt = mysqlCon.createStatement();
			String sqlStmt = "insert into aplocation values(\"" + bssid + "\", " +
					"\"" + ssid + "\", \"" + capabilities + "\", \"" + frequency +
					"\", \"" + longtitude + "\", \"" + latitude + "\")";
			System.out.println(sqlStmt);
			
			stmt.executeUpdate(sqlStmt);
			System.out.println("done");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public String getAPInfos() {
		String apInfos = "";
		
		try {
			Statement stmt = mysqlCon.createStatement();
			String sqlStmt = "select * from aplocation";
			ResultSet rs = stmt.executeQuery(sqlStmt);
			
			while (rs.next()) {
				String bssid = rs.getString("bssid");
				String ssid = rs.getString("ssid");
				String capabilities = rs.getString("capabilities");
				String frequency = rs.getString("frequency");
				String longtitude = rs.getString("longtitude");
				String latitude = rs.getString("latitude");
				
				apInfos += bssid + ", " + ssid + ", " + capabilities + ", " + frequency + ", " 
						+ longtitude + ", " + latitude + ";";
				
			}
			
			
			System.out.println("done");
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
		}
		
		return apInfos;
	}

}
