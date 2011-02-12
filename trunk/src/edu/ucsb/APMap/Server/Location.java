package edu.ucsb.APMap.Server;

public class Location {

	String longtitude;
	String latitude;
	
	public Location(String longtitude, String latitude) {
		super();
		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "Location [longtitude=" + longtitude + ", latitude=" + latitude
				+ "]";
	}
	
	
	
}
