package com.easyconnect.service;
import java.util.List;

import com.easyconnect.bean.DeveloperDeviceDetailBean;
import com.easyconnect.pojo.Developer;
import com.easyconnect.pojo.DeveloperDevice;
import com.easyconnect.pojo.DeviceType;
import com.easyconnect.pojo.Manufacturer;

public interface DeveloperService {
	
	public Developer developerLogin(String phone,String password);
		
	public boolean developerLogut(Integer developerId);
	
	public Developer developerRegister(String phone,String password, Integer manufacturerId,String name);
	
	public List<Manufacturer> getManufacturer();
	
	public List<DeveloperDevice> getDeveloperDevice(Integer developerId);
	
	public DeviceType getDeviceType(Integer deviceTypeId);
	
	
	public List<DeviceType> getAllDeviceType();
	
	public boolean addDeveloperDevice(Integer developerId,Integer typeId, String model,
			String description,String function,Integer state);
	
	public DeveloperDeviceDetailBean getDeveloperDeviceDetail(Integer developerDeviceId);
	
	public boolean modifyDeveloperPassword(Integer developerId,String password,String newPassword);
	
	//@pang
	public boolean developerDeviceOnline(Integer developerDeviceId);
	
	public int deleteDeveloperDevice(Integer developerDeviceId);
	
	
	

}
