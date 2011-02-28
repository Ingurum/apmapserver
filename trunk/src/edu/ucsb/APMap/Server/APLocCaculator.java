package edu.ucsb.APMap.Server;

import java.io.File;
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
			
			Location loc = calWifiLoc(entry.getKey(), entry.getValue());
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
	private static Location calWifiLoc(APInfo apInfo, Set<LocationLevel> scanedLocs){
		System.out.println("##############" + apInfo.getBSSID() + "#################");
		if(scanedLocs.size() < 3){
			return Average(apInfo, scanedLocs);	
		}
		else{
			List<LocationLevel> locations = pickRandom(scanedLocs);
			int losSize = scanedLocs.size();
			double samples = losSize*0.2;
			long sampl = Math.round(samples);
			int i = 0;
			Location loc;
			Set<LocationLevel> apLocs = new HashSet<LocationLevel>();
			System.out.println("Size of locations set: " + scanedLocs.size() + " Samples: " + sampl);
			for (i=0; i<sampl;i++){
				System.out.println("Mobile Location1: " + locations.get(0));
				System.out.println("Mobile Location2: " + locations.get(1));
				System.out.println("Mobile Location3: " + locations.get(2));
				
				loc = Trilateration.MyTrilateration(
						locations.get(0).getLatitude(), locations.get(0).getLongtitude(), locations.get(0).getLevel(), 
						locations.get(1).getLatitude(), locations.get(1).getLongtitude(), locations.get(1).getLevel(), 
						locations.get(2).getLatitude(), locations.get(2).getLongtitude(), locations.get(2).getLevel());
				LocationLevel locs = new LocationLevel(loc.getLongtitude(), loc.getLatitude(), 0);
				apLocs.add(locs);
			}
			return Average(apInfo, apLocs);
		}
	}
	
	/*until size of set is 3 pick random items*/
	/*
	private static Set<LocationLevel > pickRandom(Set<LocationLevel> scanedLocs){
		System.out.println("Starting pickRandom");
		int size = scanedLocs.size();
		System.out.println("scanedLocs size is: " + size);
		Set<LocationLevel> loc = new HashSet<LocationLevel>();
		System.out.println("Set locations initialized. Size is " + loc.size());
		int locSize = loc.size();
		while (locSize < 3){
			System.out.println("While loc.size == 3");
			int i = 0;
			int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
			for(LocationLevel obj : scanedLocs)
			{
			    if (i == item)
			         loc.add(obj);
			    i = i + 1;
			    System.out.println("Object: " + obj);
			    System.out.println("Set: " + loc);
			}	
			locSize = loc.size();
		}
		return loc;
	}*/
	
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
	
	private static Location Average(APInfo apInfo, Set<LocationLevel> scanedLocs) {
		System.out.println(apInfo.getBSSID() + " " + apInfo.getSSID());
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
