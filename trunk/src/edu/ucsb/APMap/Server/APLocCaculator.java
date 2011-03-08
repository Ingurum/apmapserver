package edu.ucsb.APMap.Server;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ucsb.APMap.util.APInfo;
import edu.ucsb.APMap.util.DBOp;

public class APLocCaculator {
	public static void main(String[] agrs){
		Map<APInfo, Set<LocationLevel>> scanLoc = null; //Is this correct?
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
		for(Map.Entry<APInfo, Set<LocationLevel>> entry: scanLoc.entrySet()){
			apInfo = entry.getKey();
			
			LocationLevel loc = calWifiLoc(entry.getKey(), entry.getValue());
			System.out.println("Estimated AP location: " + loc);
			
			/*
			dbInsertApInfo(apInfo.getBSSID(), 
					apInfo.getSSID(), 
					apInfo.getCapabilities(), 
					apInfo.getFrequency(), 
					loc.longtitude, 
					loc.latitude);
					*/
		}
	}
	
	// TODO compute location for each AP
	public static LocationLevel calWifiLoc(APInfo apInfo, Set<LocationLevel> scanedLocs){
		System.out.println("##############" + apInfo.getBSSID() + "#################");
		if(scanedLocs.size() < 3){
			System.out.println("Using Average.");
			return Average(apInfo, scanedLocs);	
		}
		else{
			System.out.println("Using Trilateration.");
			return Trilateration(apInfo, scanedLocs);
		}
	}
		
	public static LocationLevel Trilateration(APInfo apInfo, Set<LocationLevel> scanedLocs) {
		int samples = 3;
		ArrayList<LocationLevel> locs = new ArrayList<LocationLevel>();
		Set<LocationLevel> res = new HashSet<LocationLevel>();
		locs.addAll(scanedLocs);
		for (int i=0; i < samples; i++ ) {
			Collections.shuffle(locs);
			res.add(Trilateration.MyTrilateration(
					  locs.get(0).getLatitude(), locs.get(0).getLongtitude(), locs.get(0).getLevel(), 
					  locs.get(1).getLatitude(), locs.get(1).getLongtitude(), locs.get(1).getLevel(), 
					  locs.get(2).getLatitude(), locs.get(2).getLongtitude(), locs.get(2).getLevel())
					);
		}
		if (res.size() > 1) 
			return Average(apInfo,res);
		else
			return res.iterator().next();
		
	}
	
	public static LocationLevel SmartTrilateration(APInfo apInfo, Set<LocationLevel> scanedLocs){
		ArrayList<LocationLevel> locs;
		locs = pickThreeFurthest(scanedLocs);
		return Trilateration.MyTrilateration(
				  locs.get(0).getLatitude(), locs.get(0).getLongtitude(), locs.get(0).getLevel(), 
				  locs.get(1).getLatitude(), locs.get(1).getLongtitude(), locs.get(1).getLevel(), 
				  locs.get(2).getLatitude(), locs.get(2).getLongtitude(), locs.get(2).getLevel());
	}
		
	private static ArrayList<LocationLevel> pickThreeFurthest(Set<LocationLevel> scanedLocs){
		Iterator it = scanedLocs.iterator();
		LocationLevel loc;
		double minLat = 0, maxLat = 0, minLon = 0, maxLon = 0, 
			dLat1 = 0, dLon1 = 0, dLat2 = 0, dLon2 = 0, 
			dist1 = 0, dist2 = 0, sum_dist = 0, max_dist = 0;
		int counter = 0;
		LocationLevel minLatPt = new LocationLevel(0, 0, 0);
		LocationLevel minLonPt = new LocationLevel(0, 0, 0);
		LocationLevel maxLatPt = new LocationLevel(0, 0, 0);
		LocationLevel maxLonPt = new LocationLevel(0, 0, 0);
		LocationLevel furthestPoint = new LocationLevel(0, 0, 0);
		ArrayList<LocationLevel> result = new ArrayList<LocationLevel>();
		while (it.hasNext()){
			loc = (LocationLevel) it.next();
			if (counter == 0){
				minLat = loc.getLatitude();
				minLon = loc.getLongtitude();
				maxLat = loc.getLatitude();
				maxLon = loc.getLongtitude();
			}
			
			if (loc.getLatitude() < minLat){
				minLat = loc.getLatitude();
				minLatPt = loc;
			}
			if (loc.getLatitude() > maxLat){
				maxLat = loc.getLatitude();
				maxLatPt = loc;
			}
			if (loc.getLongtitude() < minLon){
				minLon = loc.getLongtitude();
				minLonPt = loc;
			}
			if (loc.getLongtitude() > maxLon){
				maxLon = loc.getLongtitude();
				maxLonPt = loc;
			}
			counter++;
		}
		if(minLatPt.getLatitude() != 0 && minLatPt.getLongtitude() != 0 && !result.contains(minLatPt))
			result.add(minLatPt);
		if(minLonPt.getLatitude() != 0 && minLonPt.getLongtitude() != 0 && !result.contains(minLonPt))
			result.add(minLonPt);
		if(maxLatPt.getLatitude() != 0 && maxLatPt.getLongtitude() != 0 && !result.contains(maxLatPt))
			result.add(maxLatPt);
		if(maxLonPt.getLatitude() != 0 && maxLonPt.getLongtitude() != 0 && !result.contains(maxLonPt))
			result.add(maxLonPt);
		
		if (result.size() == 3)
			System.out.println("Case three points");
		
		if (result.size() == 4){
			System.out.println("Case four points.");
			System.out.println(result);
			result.remove(3);
		}
		
		if (result.size() == 2){
			System.out.println("Case two points");
			for (LocationLevel location: scanedLocs){
				dLat1 = result.get(0).getLatitude() - location.getLatitude();
				dLon1 = result.get(0).getLongtitude() - location.getLongtitude();
				dLat2 = result.get(1).getLatitude() - location.getLatitude();
				dLon2 = result.get(1).getLongtitude() - location.getLongtitude();
				dist1 = Math.sqrt(Math.pow(dLat1, 2) + Math.pow(dLon1, 2));
				dist2 = Math.sqrt(Math.pow(dLat2, 2) + Math.pow(dLon2, 2));
				sum_dist = dist1 + dist2;
				if(max_dist < sum_dist){
					max_dist = sum_dist;
					furthestPoint = location;
				}
			}
			System.out.println("Furhtest: " + furthestPoint);
			result.add(furthestPoint);
		}
		return result;
	}
	
	
	private static List<LocationLevel > pickRandom(Set<LocationLevel> scanedLocs){
		
		List<LocationLevel> locs = new ArrayList<LocationLevel>(scanedLocs);
		Collections.shuffle(locs);
		List<LocationLevel> locations = new ArrayList<LocationLevel>();
		//Set<LocationLevel> locations;
		
		for (int i = 0; i < 3; i++)
		{
			locations.add(locs.get(i));
		}
		return locations;
	}
	
	public static LocationLevel Average(APInfo apInfo, Set<LocationLevel> scanedLocs) {
		//System.out.println(apInfo.getBSSID() + " " + apInfo.getSSID());
		int reports = scanedLocs.size();
		Iterator it = scanedLocs.iterator();
		LocationLevel loc;
		double sum_lat = 0, sum_long = 0;
		while (it.hasNext()){
			//System.out.println(it.next());
			loc = (LocationLevel) it.next();
			//System.out.println(loc.latitude);
			sum_lat += loc.latitude;
			//System.out.println(loc.longtitude);
			sum_long += loc.longtitude;
		}
		return new LocationLevel(sum_long/reports, sum_lat/reports,0);

	}

	//TODO write a method to import data to database. What is the table format in the DB.
	private static boolean dbInsertApInfo (String bssid, String ssid, String capabilities, 
			double frequency, double longtitude, double latitude) {
		
		DBOp dbop = new DBOp("localhost", "3306", "androidwifi", "android", "cs284winter");
		dbop.insertAPInfo(bssid, ssid, capabilities, frequency, longtitude, latitude);
		
		return true;
	}
}
