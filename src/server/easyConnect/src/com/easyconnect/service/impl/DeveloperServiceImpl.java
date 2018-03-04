package com.easyconnect.service.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyconnect.bean.DeveloperDeviceDetailBean;
import com.easyconnect.bean.SdkInfo;
import com.easyconnect.dao.DeveloperDao;
import com.easyconnect.dao.DeveloperDeviceDao;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.DeviceTypeDao;
import com.easyconnect.dao.FunctionDao;
import com.easyconnect.dao.ManufacturerDao;
import com.easyconnect.pojo.Developer;
import com.easyconnect.pojo.DeveloperDevice;
import com.easyconnect.pojo.DeviceInUsing;
import com.easyconnect.pojo.DeviceType;
import com.easyconnect.pojo.Fucntion;
import com.easyconnect.pojo.Manufacturer;
import com.easyconnect.service.DeveloperService;

@Service
public class DeveloperServiceImpl implements DeveloperService {

	@Autowired
	private DeveloperDao developerDao;

	@Autowired
	private ManufacturerDao manufacturerDao;
	
	@Autowired
	private DeveloperDeviceDao developerDeviceDao;
	
	@Autowired
	private DeviceTypeDao deviceTypeDao;
	
	@Autowired
	private FunctionDao functionDao;
	
	@Autowired
	private DeviceInUseDao deviceInUseDao;

	@Override
	public Developer developerLogin(String phone, String password) {
		// TODO Auto-generated method stub
		Developer developer = developerDao.getDeveloperByPhone(phone);

		if (developer!=null && developer.getPassword().equals(password)) {
			developer.setState(1);
			developerDao.update(developer);
			return developer;
		}

		return null;
	}
	
	@Override
	public boolean developerLogut(Integer developerId)//logout
	{
		Developer developer = developerDao.getDeveloperById(developerId);
		if (developer!=null)
		{
			developer.setState(0);
			developerDao.update(developer);
			return true;
		}
		return false;
	}

	@Override
	public Developer developerRegister(String phone, String password,
			Integer manufacturerId, String name) {
		// TODO Auto-generated method stub
		
		Developer developer = developerDao.getDeveloperByPhone(phone);
		
		if(developer == null)
		{
			developer = new Developer(manufacturerId, password, phone,name);
			developer.setState(0);
			developerDao.add(developer);
			
			return developer;
		}
		
		return null;
	}

	@Override
	public List<Manufacturer> getManufacturer() {
		// TODO Auto-generated method stub
		List<Manufacturer> manufacturerList = manufacturerDao.getAllManufacturer();
		return manufacturerList;
	}

	@Override
	public List<DeveloperDevice> getDeveloperDevice(Integer developerId) {
		// TODO Auto-generated method stub
		Developer developer = developerDao.getDeveloperById(developerId);
		if(developer != null && developer.getState() == 1)
		{
			List<DeveloperDevice> developerDeviceList = developerDeviceDao.getDeveloperDeviceByDeveloperId(developerId);
			return developerDeviceList;
			
		}
		return null;
	}

	@Override
	public DeviceType getDeviceType(Integer deviceTypeId) {
		// TODO Auto-generated method stub
		DeviceType deviceType = new DeviceType();
		DeviceType mydeviceType = deviceTypeDao.getOneById(deviceTypeId, deviceType);
		if(mydeviceType == null)//don't exist this device type
			return null;
		return mydeviceType;
	}

	@Override
	public List<DeviceType> getAllDeviceType() {
		// TODO Auto-generated method stub
		
		List<DeviceType> deviceTypeList = deviceTypeDao.getAllDeviceType();
		
		return deviceTypeList;
	}

	@Override
	public boolean addDeveloperDevice(Integer developerId, Integer typeId,
			String model, String description, String function, Integer state) {
		// TODO Auto-generated method stub
		
		DeviceType deviceType = new DeviceType();
		DeviceType mydeviceType = deviceTypeDao.getOneById(typeId, deviceType);
		if(mydeviceType ==null)//don't exist this device type
			return false;
		
		Developer developer = developerDao.getDeveloperById(developerId);
		if(developer ==null)//don't exist this developer
			return false;
		
		DeveloperDevice mydeveloperDevice = developerDeviceDao.getDeveloperDeviceByDeviceTypeIdAndModel(typeId, model);
		if(mydeveloperDevice!=null)
			return false;//don't exist this device-model
		
		Fucntion myfucntion = new Fucntion(function);
		functionDao.add(myfucntion);//add function to function table
		
		DeveloperDevice developerDevice = new DeveloperDevice(mydeviceType,myfucntion,developer,model,state,description);
		
		//random create Keyword with length of 9
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<9;i++)
		{
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		developerDevice.setKeyWord(sb.toString());
		
		developerDeviceDao.add(developerDevice);
		
		return true;
	}

	@Override
	public DeveloperDeviceDetailBean getDeveloperDeviceDetail(Integer developerDeviceId) {
		// TODO Auto-generated method stub
		
		DeveloperDevice developerDevice = developerDeviceDao.getDeveloperDeviceById(developerDeviceId);
		if(developerDevice==null)//don't exist this developerDevice
			return null;
		
		Integer currentOnlineDevice = 0;//the sum of the developer's Device which is online
		Integer totalOnlineDevice = 0;//the sum of all the developer's devices
		if(developerDevice.getState() ==0)//the developerDevice is complete
		{
			List<DeviceInUsing> developerInUsing = deviceInUseDao.getDevicesByDeveloperDeviceId(developerDeviceId);
			if(developerInUsing !=null)
			{
				totalOnlineDevice = developerInUsing.size();
				for(int i=0;i<totalOnlineDevice;i++)
				{
					if(developerInUsing.get(i).getState()==1)
					{
						currentOnlineDevice++;
					}
				}
			}		
		}
		
		int sdkInfoDeviceId =developerDevice.getDeveloper().getManufacturerId()*10;

		SdkInfo sdkInfo = new SdkInfo(developerDeviceId, sdkInfoDeviceId, developerDevice.getKeyWord());
		
		DeveloperDeviceDetailBean developerDeviceDetailBean = new DeveloperDeviceDetailBean(developerDeviceId, developerDevice.getDeviceType().getId(), developerDevice.getDeviceType().getTypeName(), developerDevice.getModel(), developerDevice.getState(),developerDevice.getKeyWord(), developerDevice.getFucntion().getId(), developerDevice.getFucntion().getFunctions(), developerDevice.getDescription(), currentOnlineDevice, totalOnlineDevice, sdkInfo);

		return developerDeviceDetailBean;
	}

	@Override
	public boolean developerDeviceOnline(Integer developerDeviceId) {
		// TODO Auto-generated method stub

		DeveloperDevice developerDevice = developerDeviceDao.getDeveloperDeviceById(developerDeviceId);

		if (developerDevice != null) {
			developerDevice.setState(0);
			developerDeviceDao.update(developerDevice);
			return true;
		}

		return false;
	}
	
	public boolean modifyDeveloperPassword(Integer developerId,String password,String newPassword)
	{
		Developer developer = developerDao.getDeveloperById(developerId);
		if(developer.getPassword().equals(password))
		{
			developer.setPassword(newPassword);
			developerDao.update(developer);
			return true;
		}
		return false;
	}
	
	public int deleteDeveloperDevice(Integer developerDeviceId)
	{
		DeveloperDevice developerDevice = developerDeviceDao.getDeveloperDeviceById(developerDeviceId);
		if(developerDevice ==null)
			return 1;//not exist this device
		if(developerDevice.getState() == 0)
			return 2;//this device is online,you can't delete it
		
		developerDeviceDao.delete(developerDevice);
		return 0;//delete successful
	}

}
