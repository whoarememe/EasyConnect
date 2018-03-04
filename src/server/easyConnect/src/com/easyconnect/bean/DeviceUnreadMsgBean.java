package com.easyconnect.bean;

public class DeviceUnreadMsgBean {

	private String msg;
	private Integer msgType;
	private Integer userId;
	private int random;
	
	public DeviceUnreadMsgBean(String msg, Integer msgType, Integer userId, int random) {
		super();
		this.msg = msg;
		this.msgType = msgType;
		this.userId = userId;
		this.random = random;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}
	
	
}
