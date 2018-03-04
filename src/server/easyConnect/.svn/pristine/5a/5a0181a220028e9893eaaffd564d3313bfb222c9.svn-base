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
 * Message entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "Message", schema = "dbo", catalog = "EasyConnect")
public class Message implements java.io.Serializable {

	// Fields

	private Integer id;
	private AppUser appUser;
	private DeviceInUsing deviceInUsing;
	private Integer direction;
	private Integer msgType;
	private String msg;
	private Long time;
	private Integer hasread;
	private Integer level;

	// Constructors

	/** default constructor */
	public Message() {
	}

	/** minimal constructor */
	public Message(AppUser appUser, DeviceInUsing deviceInUsing,
			Integer direction, Integer msgType, Integer hasread) {
		this.appUser = appUser;
		this.deviceInUsing = deviceInUsing;
		this.direction = direction;
		this.msgType = msgType;
		this.hasread = hasread;
	}

	/** full constructor */
	public Message(AppUser appUser, DeviceInUsing deviceInUsing,
			Integer direction, Integer msgType, String msg, Long time,
			Integer hasread, Integer level) {
		this.appUser = appUser;
		this.deviceInUsing = deviceInUsing;
		this.direction = direction;
		this.msgType = msgType;
		this.msg = msg;
		this.time = time;
		this.hasread = hasread;
		this.level = level;
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

	@Column(name = "direction", nullable = false)
	public Integer getDirection() {
		return this.direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	@Column(name = "msg_type", nullable = false)
	public Integer getMsgType() {
		return this.msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	@Column(name = "msg")
	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Column(name = "time")
	public Long getTime() {
		return this.time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	@Column(name = "hasread", nullable = false)
	public Integer getHasread() {
		return this.hasread;
	}

	public void setHasread(Integer hasread) {
		this.hasread = hasread;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return this.level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

}