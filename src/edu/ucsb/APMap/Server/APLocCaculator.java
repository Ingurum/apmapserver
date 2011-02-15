package edu.ucsb.APMap.Server;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.ucsb.APMap.util.APInfo;
import edu.ucsb.APMap.util.DBOp;

public class APLocCaculator {
	public static void main(String[] agrs){
		Map<APInfo, Set<Location>> scanLoc = null; //Is this correct?
		String traceDirectory = "/home/mariya/Dropbox/Winter2011/CS284/project/data/";
		File dir = new File(traceDirectory);
		String[] files = dir.list();
		if(files == null){
			System.out.println("Failed to open directory.");
		}
		else {
			for (int i=0; i<files.length; i++){
				System.out.println(files[i]);
			}
			for (int i=0; i<files.length; i++){
				//What's the max size of a Map? Seems scanLoc can't fit everything I have.
				scanLoc = new TraceParser().parse(traceDirectory + "/" + files[i]);
			}
			 
		}
		APInfo apInfo;
		for(Map.Entry<APInfo, Set<Location>> entry: scanLoc.entrySet()){
			apInfo = entry.getKey();
			
			Location loc = calWifiLoc(entry.getKey(), entry.getValue());
			
			dbInsertApInfo(apInfo.getBSSID(), 
					apInfo.getSSID(), 
					apInfo.getCapabilities(), 
					apInfo.getFrequency(), 
					loc.longtitude, 
					loc.latitude);
		}
	}
	
	// TODO compute location for each AP
	private static Location calWifiLoc(APInfo apInfo, Set<Location> scanedLocs){
		System.out.println(apInfo.getBSSID() + " " + apInfo.getSSID());
		int reports = scanedLocs.size();
		Iterator it = scanedLocs.iterator();
		Location loc;
		double sum_lat = 0, sum_long = 0;
		while (it.hasNext()){
			//System.out.println(it.next());
			loc = (Location) it.next();
			//System.out.println(loc.latitude);
			sum_lat += loc.latitude;
			//System.out.println(loc.longtitude);
			sum_long += loc.longtitude;
		}
		System.out.println(sum_lat/reports);
		System.out.println(sum_long/reports);
		return new Location(sum_long/reports,sum_lat/reports);
	}
	
	//TODO write a method to import data to database. What is the table format in the DB.
	private static boolean dbInsertApInfo (String bssid, String ssid, String capabilities, 
			double frequency, double longtitude, double latitude) {
		
		DBOp dbop = new DBOp("localhost", "3306", "androidwifi", "android", "cs284winter");
		dbop.insertAPInfo(bssid, ssid, capabilities, frequency, longtitude, latitude);
		
		return true;
	}
}
