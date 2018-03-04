package com.easyconnect.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AppUser", schema = "dbo", catalog = "EasyConnect")
public class AppUser implements java.io.Serializable {

	private Integer id;
	private String name;
	private String password;
	private String phone;
	private String mail;
	private String ip;
	private Integer port;
	private Integer state;
	private Long heartTime;


	/** default constructor */
	public AppUser() {
	}

	/** minimal constructor */
	public AppUser(String name, String password, String phone, String mail,
			Integer state) {
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.mail = mail;
		this.state = state;
	}

	/** full constructor */
	public AppUser(String name, String password, String phone, String mail,
			String ip, Integer port, Integer state, Long heartTime) {
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.mail = mail;
		this.ip = ip;
		this.port = port;
		this.state = state;
		this.heartTime = heartTime;
	}


	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phone", nullable = false)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "mail", nullable = false)
	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "port")
	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Column(name = "state", nullable = false)
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "heartTime")
	public Long getHeartTime() {
		return this.heartTime;
	}

	public void setHeartTime(Long heartTime) {
		this.heartTime = heartTime;
	}

}