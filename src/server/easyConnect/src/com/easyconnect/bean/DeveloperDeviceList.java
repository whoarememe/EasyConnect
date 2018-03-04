package com.easyconnect.bean;


public class DeveloperDeviceList {
	
	private Integer developerDevideId;
	private Integer deviceTypeId;
	private String deviceType;
	private String model;
	private Integer state;
	private String keyWord;
	private Integer fucntionId;
	private String description;
	
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public DeveloperDeviceList(Integer developerDevideId, Integer deviceTypeId,
			String deviceType, String model, Integer state, String keyWord,
			Integer fucntionId, String description) {
		super();
		this.developerDevideId = developerDevideId;
		this.deviceTypeId = deviceTypeId;
		this.deviceType = deviceType;
		this.model = model;
		this.state = state;
		this.keyWord = keyWord;
		this.fucntionId = fucntionId;
		this.description = description;
	}
	public Integer getDeveloperDevideId() {
		return developerDevideId;
	}
	public void setDeveloperDevideId(Integer developerDevideId) {
		this.developerDevideId = developerDevideId;
	}
	public Integer getDeviceTypeId() {
		return deviceTypeId;
	}
	public void setDeviceTypeId(Integer deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
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
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Integer getFucntionId() {
		return fucntionId;
	}
	public void setFucntionId(Integer fucntionId) {
		this.fucntionId = fucntionId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
