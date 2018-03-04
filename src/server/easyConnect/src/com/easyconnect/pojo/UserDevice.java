package com.easyconnect.pojo;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * UserDevice entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "UserDevice", schema = "dbo", catalog = "EasyConnect")
public class UserDevice implements java.io.Serializable {

	// Fields

	private Integer id;
	private AppUser appUser;
	private DeviceInUsing deviceInUsing;
	private Integer authority;

	// Constructors

	/** default constructor */
	public UserDevice() {
	}

	/** full constructor */
	public UserDevice(AppUser appUser, DeviceInUsing deviceInUsing,
			Integer authority) {
		this.appUser = appUser;
		this.deviceInUsing = deviceInUsing;
		this.authority = authority;
	}

	// Property accessors
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id", nullable = false)
	public DeviceInUsing getDeviceInUsing() {
		return this.deviceInUsing;
	}

	public void setDeviceInUsing(DeviceInUsing deviceInUsing) {
		this.deviceInUsing = deviceInUsing;
	}

	@Column(name = "authority", nullable = false)
	public Integer getAuthority() {
		return this.authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}

}