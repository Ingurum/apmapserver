/*Class stores AP info*/
package edu.ucsb.APMap.util;

public class APInfo {
	
	private String BSSID;
	private String SSID;
	private String capabilities;
	private double frequency;
	private int level;
	private double longtitude;
	private double latitude;
	
	public APInfo(String bSSID, String sSID, String capabilities,
			double frequency, int level) {
		super();
		BSSID = bSSID;
		SSID = sSID;
		this.capabilities = capabilities;
		this.frequency = frequency;
		this.level = level;
	}
	
	public APInfo(String bSSID, String sSID, String capabilities,
			double frequency, double longtitude, double latitude) {
		super();
		BSSID = bSSID;
		SSID = sSID;
		this.capabilities = capabilities;
		this.frequency = frequency;
		this.longtitude = longtitude;
		this.latitude = latitude;
	}
	
	public APInfo(String bSSID, String sSID, String capabilities,
			double frequency, int level, double longtitude, double latitude) {
		super();
		BSSID = bSSID;
		SSID = sSID;
		this.capabilities = capabilities;
		this.frequency = frequency;
		this.level = level;
		this.longtitude = longtitude;
		this.latitude = latitude;
	}

	public String getBSSID() {
		return BSSID;
	}

	public void setBSSID(String bSSID) {
		BSSID = bSSID;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((SSID == null) ? 0 : SSID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		APInfo other = (APInfo) obj;
		if (SSID == null) {
			if (other.SSID != null)
				return false;
		} else if (!SSID.equals(other.SSID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "APInfo [BSSID=" + BSSID + ", SSID=" + SSID + ", capabilities="
				+ capabilities + ", frequency=" + frequency + ", level="
				+ level + ", longtitude=" + longtitude + ", latitude="
				+ latitude + "]";
	}
	
	
}
