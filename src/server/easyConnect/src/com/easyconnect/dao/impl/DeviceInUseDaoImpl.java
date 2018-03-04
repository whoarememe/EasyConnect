package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.bean.DeviceDataBean;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.pojo.DeviceInUsing;


@Repository
public class DeviceInUseDaoImpl extends BaseDaoImpl implements DeviceInUseDao {

	@Override
	public List<DeviceDataBean> getDevicesByUserId(String UserId) {
		// TODO Auto-generated method stub
		String hql = "selcet du.device_id from DeviceUser as du,AppUser as au where du.user_id = as.id";
		return null;
	}
	
	@Override
	public DeviceInUsing getDeviceByDeviceId(Integer deviceId)
	{
		System.out.println("device id is :"  + deviceId);
		String hql = "from DeviceInUsing as du where du.deviceId = ?";
		List<DeviceInUsing> deviceInUsingList =  (List<DeviceInUsing>)this.getHibernateTemplate().find(hql,deviceId);
		
		DeviceInUsing deviceInUsing;
		if(deviceInUsingList.size()==0)
		{
			deviceInUsing = null;
		}else
		{
			deviceInUsing = deviceInUsingList.get(0);
		}
		
		return deviceInUsing;
	}
	
	@Override
	public List<DeviceInUsing> getDevicesByDeveloperDeviceId(Integer developerDeviceId)
	{
		String hql = "from DeviceInUsing as du where du.developerDevice.id = ?";
		List<DeviceInUsing> deviceInUsingList =  (List<DeviceInUsing>)this.getHibernateTemplate().find(hql,developerDeviceId);
		
		if(null == deviceInUsingList || 0 == deviceInUsingList.size())
			return null;
		else
			return deviceInUsingList;
		
	}

	@Override
	public List<DeviceInUsing> getDevicesOnline() {
		// TODO Auto-generated method stub
		String hqlString = "from DeviceInUsing as du where du.state = 1";
		List<DeviceInUsing> deviceInUsings = (List<DeviceInUsing>)this.getHibernateTemplate().find(hqlString);
		
		if (deviceInUsings == null || deviceInUsings.size() == 0) return null;
		else return deviceInUsings;
	}

}