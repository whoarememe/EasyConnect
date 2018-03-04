package com.easyconnect.bean;

public class DeveloperDeviceDetailBean {
	
	private Integer developerDeviceId;
	
	private Integer devideTypeId;
	
	private String deviceType;
	
	private String model;
	
	private Integer state;
	
	private String keyword;
	
	private Integer functionId;
	
	private String functions;
	
	private String description;
	
	private Integer currentOnlineDevice;
	private Integer totalOnlineDevice;
	
	private SdkInfo sdkInfo;

	
	public DeveloperDeviceDetailBean(Integer developerDeviceId,
			Integer devideTypeId, String deviceType, String model,
			Integer state, String keyword, Integer functionId,
			String functions, String description, Integer currentOnlineDevice,
			Integer totalOnlineDevice, SdkInfo sdkInfo) {
		super();
		this.developerDeviceId = developerDeviceId;
		this.devideTypeId = devideTypeId;
		this.deviceType = deviceType;
		this.model = model;
		this.state = state;
		this.keyword = keyword;
		this.functionId = functionId;
		this.functions = functions;
		this.description = description;
		this.currentOnlineDevice = currentOnlineDevice;
		this.totalOnlineDevice = totalOnlineDevice;
		this.sdkInfo = sdkInfo;
	}

	

	public String getKeyword() {
		return keyword;
	}



	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}



	public Integer getCurrentOnlineDevice() {
		return currentOnlineDevice;
	}


	public void setCurrentOnlineDevice(Integer currentOnlineDevice) {
		this.currentOnlineDevice = currentOnlineDevice;
	}


	public Integer getTotalOnlineDevice() {
		return totalOnlineDevice;
	}


	public void setTotalOnlineDevice(Integer totalOnlineDevice) {
		this.totalOnlineDevice = totalOnlineDevice;
	}


	public Integer getDeveloperDeviceId() {
		return developerDeviceId;
	}

	public void setDeveloperDeviceId(Integer developerDeviceId) {
		this.developerDeviceId = developerDeviceId;
	}

	public Integer getDevideTypeId() {
		return devideTypeId;
	}

	public void setDevideTypeId(Integer devideTypeId) {
		this.devideTypeId = devideTypeId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getFunctionId() {
		return functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getFunctions() {
		return functions;
	}

	public void setFunctions(String functions) {
		this.functions = functions;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public SdkInfo getSdkInfo() {
		return sdkInfo;
	}

	public void setSdkInfo(SdkInfo sdkInfo) {
		this.sdkInfo = sdkInfo;
	}
	
	

}
