package com.easyconnect.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyconnect.bean.UserDeviceTmp;
import com.easyconnect.dao.DeveloperDao;
import com.easyconnect.dao.DeveloperDeviceDao;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.ManufacturerDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.dao.UserDeviceDao;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.Developer;
import com.easyconnect.pojo.DeveloperDevice;
import com.easyconnect.pojo.DeviceInUsing;
import com.easyconnect.pojo.DeviceType;
import com.easyconnect.pojo.Manufacturer;
import com.easyconnect.pojo.UserDevice;
import com.easyconnect.service.UserDeviceService;

@Service
public class UserDeviceServiceImpl implements UserDeviceService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserDeviceDao userDeviceDao;
	@Autowired
	private DeveloperDeviceDao developerDeviceDao;
	@Autowired
	private DeveloperDao developerDao;
	@Autowired
	private ManufacturerDao manufacturerDao;
	@Autowired
	private DeviceInUseDao deviceInUseDao;

	@Override
	public List<UserDeviceTmp> getUserDeviceInfo(int userId) {
		List<UserDevice> userDeviceList = userDeviceDao.getUserDevice(userId);
		int developerDeviceId;
		List<UserDeviceTmp> userDeviceTmpList = new ArrayList<UserDeviceTmp>();
		
		if(null == userDeviceList)
			return null;	//don't exist this user
		
		for(UserDevice temp : userDeviceList) {
			developerDeviceId = temp.getDeviceInUsing().getDeveloperDevice().getId();
			System.out.println(developerDeviceId);
			DeveloperDevice developerDevice = developerDeviceDao.getDeveloperDeviceById(developerDeviceId);
			Developer developer = developerDevice.getDeveloper();
			Manufacturer manufacturer = manufacturerDao.getManufacturerById(developer.getManufacturerId());
			DeviceType deviceType = developerDevice.getDeviceType();
			
			UserDeviceTmp userDeviceTmp = new UserDeviceTmp();
			userDeviceTmp.setDescription(developerDevice.getDescription());
			userDeviceTmp.setDeveloperId(developerDevice.getDeveloper().getId());
			userDeviceTmp.setDeviceId(temp.getDeviceInUsing().getDeviceId());
			userDeviceTmp.setFunctionId(developerDevice.getFucntion().getId());
			userDeviceTmp.setManufacturer(manufacturer.getName());
			userDeviceTmp.setModel(developerDevice.getModel());
			userDeviceTmp.setPic("12");
			userDeviceTmp.setType(deviceType.getTypeName());
			userDeviceTmpList.add(userDeviceTmp);
		}
		return userDeviceTmpList;
	}
	
	@Override
	public UserDeviceTmp getNewAdDevice(UserDevice device)
	{
		int developerDeviceId = device.getDeviceInUsing().getDeveloperDevice().getId();
		System.out.println(developerDeviceId);
		DeveloperDevice developerDevice = developerDeviceDao.getDeveloperDeviceById(developerDeviceId);
		Developer developer = developerDevice.getDeveloper();
		Manufacturer manufacturer = manufacturerDao.getManufacturerById(developer.getManufacturerId());
		DeviceType deviceType = developerDevice.getDeviceType();
		
		UserDeviceTmp userDeviceTmp = new UserDeviceTmp();
		userDeviceTmp.setDescription(developerDevice.getDescription());
		userDeviceTmp.setDeveloperId(developerDevice.getDeveloper().getId());
		userDeviceTmp.setDeviceId(device.getDeviceInUsing().getDeviceId());
		userDeviceTmp.setFunctionId(developerDevice.getFucntion().getId());
		userDeviceTmp.setManufacturer(manufacturer.getName());
		userDeviceTmp.setModel(developerDevice.getModel());
		userDeviceTmp.setPic("12");
		userDeviceTmp.setType(deviceType.getTypeName());
		return userDeviceTmp;
	}
	
	@Override
	public UserDevice addDevice(Integer userId, Integer deviceId,
			String devPasswd) {
		// TODO Auto-generated method stub
		DeviceInUsing deviceInUsing = deviceInUseDao.getDeviceByDeviceId(deviceId);
		
		AppUser user = userDao.getUserByUserId(userId);
		
		UserDevice userDevice;
		
		if(deviceInUsing == null)
		{
			userDevice = new UserDevice(null, null, 0);
		}
		else
		{
			
			userDevice = userDeviceDao.getgetUserDeviceByUserIdAndDeviceId(userId, deviceId);
			if(userDevice!=null)
			{
				userDevice.setAuthority(-1);//already  exist this Device in this user's table
				//return userDevice;
			}
			else if(deviceInUsing.getPassword().equals(devPasswd)&& user != null)
			{
				userDevice = new UserDevice(user,deviceInUsing,2);
				userDeviceDao.add(userDevice);
			}
			else
			{
				userDevice = null;//password is wrong
			}
		}
		return userDevice;
	}
	
	@Override
	public UserDevice shareDevice(String phone,Integer deviceId)
	{
		UserDevice userDevice;
		
		AppUser appUser = userDao.getUserByTelePhone(phone);
		DeviceInUsing deviceInUsing = deviceInUseDao.getDeviceByDeviceId(deviceId);
		if(appUser ==null)
		{
			userDevice = new UserDevice(null, null, -2);//don't exist this user
		}
		else if(deviceInUsing ==null)
		{
			userDevice = new UserDevice(null, null, -1);//don't exist this device
		}
		else
		{
			userDevice = userDeviceDao.getgetUserDeviceByUserIdAndDeviceId(appUser.getId(), deviceId);
			if(userDevice != null)
			{
				userDevice = new UserDevice(null, null, 0);//already  exist this Device in this user's table
			}
			else
			{
				userDevice = new UserDevice(appUser, deviceInUsing, 1);//add success
				userDeviceDao.add(userDevice);
			}
		}
		return userDevice;
	}
	
	@Override
	public int modifyDevicePassword(Integer userId,Integer deviceId, String password,
			String newPassword) {
		// TODO Auto-generated method stub
		
		UserDevice userDevice = userDeviceDao.getgetUserDeviceByUserIdAndDeviceId(userId, deviceId);
		if(userDevice ==null)
			return 3;
		if(userDevice.getAuthority()==1)
			return 1;
		
		DeviceInUsing device = deviceInUseDao.getDeviceByDeviceId(deviceId);
		if(device ==null)
			return 4;
		if(device.getPassword().equals(password))
		{
			device.setPassword(newPassword);
			deviceInUseDao.update(device);
			return 0;
		}
		else
			return 2;
		
	}

}
