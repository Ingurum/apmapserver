package edu.ucsb.APMap.Server;

public class Location {

	double longtitude;
	double latitude;
	
	public Location(double longtitude, double latitude) {
		super();
		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(double longtitude) {
		this.longtitude = longtitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
	
}
