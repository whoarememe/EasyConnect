package com.easyconnect.dao;

import java.util.List;

import com.easyconnect.pojo.DeveloperDevice;

public interface DeveloperDeviceDao extends BaseDao {

	DeveloperDevice getDeveloperDeviceById(int developerDeviceId);
	
	public List<DeveloperDevice> getDeveloperDeviceByDeveloperId(Integer developerId);
	
	public DeveloperDevice getDeveloperDeviceByDeviceTypeIdAndModel(Integer deviceByDeviceTypeId,String model);
}
