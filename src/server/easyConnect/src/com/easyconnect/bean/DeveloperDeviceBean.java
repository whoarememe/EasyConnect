package com.easyconnect.bean;

import java.util.List;

import javax.persistence.Entity;

@Entity
public class DeveloperDeviceBean {
	
	private Integer developerId;
	
	private List<DeveloperDeviceList> developerDeviceList;

	public DeveloperDeviceBean(Integer developerId,
			List<DeveloperDeviceList> developerDeviceList) {
		super();
		this.developerId = developerId;
		this.developerDeviceList = developerDeviceList;
	}

	public Integer getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(Integer developerId) {
		this.developerId = developerId;
	}

	public List<DeveloperDeviceList> getDeveloperDeviceList() {
		return developerDeviceList;
	}

	public void setDeveloperDeviceList(List<DeveloperDeviceList> developerDeviceList) {
		this.developerDeviceList = developerDeviceList;
	}
	
	

}
