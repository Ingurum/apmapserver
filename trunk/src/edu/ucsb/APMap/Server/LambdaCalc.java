package edu.ucsb.APMap.Server;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.ucsb.APMap.util.APInfo;

public class LambdaCalc {
	
	public static double calcLambda(double apLT,double apLG,Map<APInfo, Set<LocationLevel>> scanLoc){
		double lambda = 0;
		double mobLT = 0; //mobile's latitude
		double mobLG = 0; //mobile's longitude
		int mobLevel = 0; //RSSI at the mobile
		double dist = 0; //distance from AP to mobile
		APInfo apInfo = null;
		Set<LocationLevel> locs = null;
		LocationLevel loc = null;
		for(Map.Entry<APInfo, Set<LocationLevel>> entry: scanLoc.entrySet()){
			apInfo = entry.getKey();
			locs = entry.getValue();
			int samples = locs.size();
			Iterator it = locs.iterator();
			double sumLambds = 0;
			if(apInfo.getBSSID().equals("GizmoDo")){
				while(it.hasNext()){
					System.out.println("#################################");
					loc = (LocationLevel) it.next(); // mobile location
					mobLT = loc.getLatitude();
					System.out.println("Mobile's LT: " + mobLT);
					mobLG = loc.getLongtitude();
					System.out.println("Mobile's LG: " + mobLG);
					mobLevel = loc.getLevel();
					System.out.println("Mobile's RSSI: " + mobLevel);
					dist = LambdaCalc.distance(apLT, apLG, mobLT, mobLG);
					System.out.println("Mobile's dist: " + dist);
					System.out.println("log of dist" + Math.log10(dist) + "Level" + mobLevel);
					System.out.println("A lambda: " + mobLevel/(Math.log10(dist)));
					sumLambds += mobLevel/(Math.log10(dist));
					System.out.println("Mobile's sum lambdas is: " + sumLambds);
				}
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("sum lambda: " + sumLambds + " Samples: " + samples);
				lambda = sumLambds/samples;
				System.out.println("Mobile's lambda: " + lambda);
			}
		}
		return lambda;
	}
	
	// Calculating distance between two points 
	// given their latitude and longitude
	// Using HAVERSINE formula.
	public static double distance(double apLT, double apLG, double mobLT, double mobLG){
		long R = 6371; // Earth radius
		double dLat = Math.toRadians((apLT - mobLT));
		System.out.println("dLat: " + dLat);
		double dLon = Math.toRadians((apLG - mobLG));
		System.out.println("dLon: " + dLon);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        		Math.cos(Math.toRadians(mobLT)) * 
        		Math.cos(Math.toRadians(apLT)) * 
        		Math.sin(dLon/2) * Math.sin(dLon/2);
		System.out.println("a" + a);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		System.out.println("c" + c);
		double dist = R * c;
		return dist;
	}
	
	public static void main(String args[]) throws Exception{
		double apLT = 34.4223179;
		double apLG = -119.86251341666667;
		String traceDirectory = "/home/mariya/Dropbox/Winter2011/CS284/project/lambda_data/GizmoDo.txt";
		Map<APInfo, Set<LocationLevel>> scanLoc = new TraceParser().parse(traceDirectory);
		double lambda = LambdaCalc.calcLambda(apLT, apLG, scanLoc);
		System.out.println("GizmoDo Lambda is:" + lambda);
	}
}


