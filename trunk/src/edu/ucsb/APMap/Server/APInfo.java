package edu.ucsb.APMap.Server;

public class APInfo {
	
	private String BSSID;
	private String SSID;
	private String capabilities;
	private int frequency;
	private int level;
	
	public APInfo(String bSSID, String sSID, String capabilities,
			int frequency, int level) {
		super();
		BSSID = bSSID;
		SSID = sSID;
		this.capabilities = capabilities;
		this.frequency = frequency;
		this.level = level;
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

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
}
