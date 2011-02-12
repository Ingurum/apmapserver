/*Class stores AP info*/
package edu.ucsb.APMap.Server;

public class APInfo {
	
	private String BSSID;
	private String SSID;
	private String capabilities;
	private String frequency;
	private String level;
	private String longtitude;
	private String latitude;
	
	public APInfo(String bSSID, String sSID, String capabilities,
			String frequency, String level) {
		super();
		BSSID = bSSID;
		SSID = sSID;
		this.capabilities = capabilities;
		this.frequency = frequency;
		this.level = level;
	}
	
	public APInfo(String bSSID, String sSID, String capabilities,
			String frequency, String level, String longtitude, String latitude) {
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

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
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
