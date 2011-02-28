package edu.ucsb.APMap.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.ucsb.APMap.util.APInfo;

public class TraceParser {
	
	public static Map<APInfo, Set<LocationLevel>> parse(String traceDirectory)
	{
		Map<APInfo, Set<LocationLevel>> apInfoMap = new HashMap<APInfo, Set<LocationLevel>>();
		try {
			BufferedReader br;
			
			br = new BufferedReader(new FileReader(traceDirectory));
			
			String line;
			String[] elems;
			Location loc = null;
			LocationLevel loclev = null;
			APInfo ap ;
			
			while((line = br.readLine()) != null) {
				if (line.equals("Beginning_LOC")) {
					while (!(line = br.readLine()).equals("Ending_LOC")){
						elems = line.split(",");
						if (elems.length == 3){
							loc = new Location(Double.parseDouble(elems[1]), Double.parseDouble(elems[0]));	
						}	
					}
				}
				//We can set up a hash for TimeStamp as well.
				if (line.equals("Beginning_APs")) {
					while (!(line = br.readLine()).equals("Ending_APs")){
						elems = line.split(",");
						if(elems.length == 5){
							//System.out.println(line);
							ap = new APInfo(elems[0], elems[3], elems[4], Double.parseDouble(elems[2]), Integer.parseInt(elems[1]));
							loclev = new LocationLevel(loc.getLongtitude(), loc.getLatitude(), ap.getLevel());
							if(apInfoMap.containsKey(ap)){
								apInfoMap.get(ap).add(loclev);
							}
							else{
								Set<LocationLevel> locationlevs = new HashSet<LocationLevel>();
								locationlevs.add(loclev);
								apInfoMap.put(ap, locationlevs);
							}	
						}		
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			for(Map.Entry<APInfo, Set<LocationLevel>> entry: apInfoMap.entrySet()){
				//System.out.println(entry.getKey().toString());
				for(LocationLevel loc: entry.getValue()){
					//System.out.println(loc.toString());
				}
			}
			return apInfoMap;
		}
	}
	
	
	//Local main for test purposes
	
	
	public static void main(String args[]) throws Exception
    {
		Map<APInfo, Set<LocationLevel>> scanLoc = null;
		String traceDirectory = "/home/mariya/Dropbox/Winter2011/CS284/project/data/";
		File dir = new File(traceDirectory);
		String[] files = dir.list();
		if(files == null){
			System.out.println("Failed to open directory.");
		}
		else {
			for (int i=0; i<files.length; i++){
				System.out.println("################" + files[i] + "##############\n");
				scanLoc = new TraceParser().parse(traceDirectory + "/" + files[i]);
				//System.out.println(scanLoc);
			}
		}
		//TraceParser.parse("/home/mariya/ap-02-10-10.txt");
		//TraceParser.parse("/home/mariya/Dropbox/Winter2011/CS284/project/data/ap-02-14-01.txt");
		System.out.println("Done");
    }
	
}

