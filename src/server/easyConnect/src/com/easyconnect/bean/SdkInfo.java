package com.easyconnect.bean;

public class SdkInfo {
	
	private Integer developerDeviceId;
	
	private Integer deviceId;
	
	private String key;

	public SdkInfo(Integer developerDeviceId, Integer deviceId, String key) {
		super();
		this.developerDeviceId = developerDeviceId;
		this.deviceId = deviceId;
		this.key = key;
	}

	public Integer getDeveloperDeviceId() {
		return developerDeviceId;
	}

	public void setDeveloperDeviceId(Integer developerDeviceId) {
		this.developerDeviceId = developerDeviceId;
	}

	public Integer getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	

}
