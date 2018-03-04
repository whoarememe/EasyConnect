package com.easyconnect.bean;

import javax.persistence.Entity;

@Entity
public class UserDataBean {
	private int id;
	private String name;
	private String phone;
	private String mail;
	
	public UserDataBean(int id,String name,String phone,String mail) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.mail = mail;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
}
