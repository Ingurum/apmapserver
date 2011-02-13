package edu.ucsb.APMap.Server;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class APLocCaculator {
	
	public static void main(String[] agrs){
		String traceDirectory = "./";
		Map<APInfo, Set<Location>> scanLoc = new TraceParser().parse(traceDirectory);
		for(Map.Entry<APInfo, Set<Location>> entry: scanLoc.entrySet()){
			Location loc = calWifiLoc(entry.getKey(), entry.getValue());
			
		}
		
	}
	
	// compute location for one AP
	private static Location calWifiLoc(APInfo apInfo, Set<Location> scanLocs){
		return new Location("", "");
	}
	
	
	
}
