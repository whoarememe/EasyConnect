package com.easyconnect.dao;

import java.util.List;

import com.easyconnect.bean.DeviceDataBean;
import com.easyconnect.pojo.DeviceInUsing;

public interface DeviceInUseDao extends BaseDao {
	
	public List<DeviceDataBean> getDevicesByUserId(String UserId);
	
	public DeviceInUsing getDeviceByDeviceId(Integer deviceId);
	
	public List<DeviceInUsing> getDevicesByDeveloperDeviceId(Integer developerDeviceId);
	
	public List<DeviceInUsing> getDevicesOnline();
}
