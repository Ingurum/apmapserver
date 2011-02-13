package edu.ucsb.APMap.Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TraceParser {
	
	public static Map<APInfo, Set<Location>> parse(String traceDirectory)
	{
		Map<APInfo, Set<Location>> apInfoMap = new HashMap<APInfo, Set<Location>>();
		try {
			BufferedReader br;
			
				br = new BufferedReader(new FileReader(traceDirectory));
			
			
			String line;
			String[] elems;
			Location loc = null;
			APInfo ap ;
			
			while((line = br.readLine()) != null) {
				if (line.equals("Beginning_LOC")) {
					while (!(line = br.readLine()).equals("Ending_LOC")){
						elems = line.split(",");
						if (elems.length == 3){
							loc = new Location(elems[1], elems[0]);	
						}	
					}
				}
				//We can set up a hash for TimeStamp as well.
				if (line.equals("Beginning_APs")) {
					while (!(line = br.readLine()).equals("Ending_APs")){
						elems = line.split(",");
						if(elems.length == 5){
							System.out.println(line);
							ap = new APInfo(elems[0], elems[3], elems[4], elems[2], elems[1]);
							if(apInfoMap.containsKey(ap)){
								apInfoMap.get(ap).add(loc);
							}
							else{
								Set<Location> locations = new HashSet<Location>();
								locations.add(loc);
								apInfoMap.put(ap, locations);
							}	
						}		
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			for(Map.Entry<APInfo, Set<Location>> entry: apInfoMap.entrySet()){
				System.out.println(entry.getKey().toString());
				for(Location loc: entry.getValue()){
					System.out.println(loc.toString());
				}
			}
			return apInfoMap;
		}
	}
	
	
	//Local main for test purposes
	public static void main(String args[]) throws Exception
    {
		TraceParser.parse("/home/mariya/ap-02-10-10.txt");
    }
	
}

