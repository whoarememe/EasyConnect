package com.easyconnect.dao;

import java.util.List;

import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.UserDevice;


public interface UserDeviceDao extends BaseDao {
	List<UserDevice> getUserDevice(int userId);
	
	List<AppUser> getUsersByDeviceId(int deviceId);
	
	public UserDevice getgetUserDeviceByUserIdAndDeviceId(int userId,int deviceId);

}
