package edu.ucsb.APMap.Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TraceParser {
	
	public static Map<APInfo, List<Location>> parse(String traceDirectory)
	{
		Map<APInfo, List<Location>> apInfoMap = new HashMap<APInfo, List<Location>>();
		try {
			BufferedReader br;
			
				br = new BufferedReader(new FileReader(traceDirectory));
			
			
			String line;
			String[] elems;
			Location loc;
			APInfo ap;
			
			while((line = br.readLine()) != null) {
				if (line.equals("Beginning_LOC")) {
					while (!(line = br.readLine()).equals("Ending_LOC")){
						elems = line.split(",");
						loc = new Location(elems[1], elems[0]);	
					}
				}
				//We can set up a hash for TimeStamp as well.
				if (line.equals("Beginning_APs")) {
					while (!(line = br.readLine()).equals("End_APs")){
						elems = line.split(",");
						ap = new APInfo(elems[0], elems[3], elems[4], elems[2], elems[1]);	
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			return apInfoMap;
		}
	}
	
	
	/* Local main for test purposes
	public static void main(String args[]) throws Exception
    {
		TraceParser.parse("/home/mariya/ap-02-10-10.txt");
    }*/
	
}

