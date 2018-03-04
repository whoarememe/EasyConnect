package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.DeviceTypeDao;
import com.easyconnect.pojo.DeveloperDevice;
import com.easyconnect.pojo.DeviceType;

@Repository
public class DeviceTypeDaoImpl extends BaseDaoImpl implements DeviceTypeDao {

	@Override
	public List<DeviceType> getAllDeviceType() {
		// TODO Auto-generated method stub
		String hql = "from DeviceType";
		List<DeviceType> deviceTypeList = (List<DeviceType>)this.getHibernateTemplate().find(hql);
		
		if(null == deviceTypeList || 0 == deviceTypeList.size())
			return null;
		else
			return deviceTypeList;
	}

}
