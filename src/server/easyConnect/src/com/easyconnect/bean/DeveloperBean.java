package com.easyconnect.bean;

import javax.persistence.Entity;

@Entity
public class DeveloperBean {
	
	private Integer developerId;
	
	private String developerName;
	
	private String phone;
	
	private int manufacturerId;
	
//	private String manufacturerName;


	public DeveloperBean(Integer developerId, String developerName,
			String phone, int manufacturerId) {
		super();
		this.developerId = developerId;
		this.developerName = developerName;
		this.phone = phone;
		this.manufacturerId = manufacturerId;
	}

	public int getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(int manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public Integer getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(Integer developerId) {
		this.developerId = developerId;
	}

	public String getDeveloperName() {
		return developerName;
	}

	public void setDeveloperName(String developerName) {
		this.developerName = developerName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
		
}
