package com.easyconnect.service;

import java.util.List;

import com.easyconnect.bean.UserDeviceTmp;
import com.easyconnect.pojo.UserDevice;

public interface UserDeviceService {
	List<UserDeviceTmp> getUserDeviceInfo(int userId);
	
	public UserDeviceTmp getNewAdDevice(UserDevice device);
	
	public UserDevice addDevice(Integer userId,Integer deviceId,String devPasswd);
	
	public int modifyDevicePassword(Integer userId,Integer deviceId, String password,String newPassword);
	
	public UserDevice shareDevice(String phone,Integer deviceId);

}
