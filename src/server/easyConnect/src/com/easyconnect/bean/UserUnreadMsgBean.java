package com.easyconnect.bean;

import javax.persistence.Entity;

@Entity
public class UserUnreadMsgBean {
	
	private Integer direction;
	private Integer deviceId;
	private Integer userId;
	private Integer msgType;
	private String msg;
	private Long time;
	private Integer hasread;
	private Integer level;
	
	public UserUnreadMsgBean(Integer direction, Integer deviceId,
			Integer userId, Integer msgType, String msg, Long time,
			Integer hasread,Integer level) {
		super();
		this.direction = direction;
		this.deviceId = deviceId;
		this.userId = userId;
		this.msgType = msgType;
		this.msg = msg;
		this.time = time;
		this.hasread = hasread;
		this.level = level;
	}
	
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Integer getHasread() {
		return hasread;
	}
	public void setHasread(Integer hasread) {
		this.hasread = hasread;
	}
	

}
