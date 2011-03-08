package edu.ucsb.APMap.Server;

public class LocationLevel {
	
	double longtitude;
	double latitude;
	int level;
	
	public LocationLevel(double longtitude, double latitude, int level) {
		super();
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.level = level;
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	@Override
	public String toString() {
		return "Location [latitude=" + latitude + 
			", longitude=" + longtitude + ", level=" + level + "]";
	}
	
}
