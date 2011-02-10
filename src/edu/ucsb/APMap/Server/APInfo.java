/*Class stores AP info*/
package edu.ucsb.APMap.Server;

public class APInfo {
	
	private String BSSID;
	private String SSID;
	private String capabilities;
	private String frequency;
	private String level;
	
	public APInfo(String bSSID, String sSID, String capabilities,
			String frequency, String level) {
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
	
	
}
