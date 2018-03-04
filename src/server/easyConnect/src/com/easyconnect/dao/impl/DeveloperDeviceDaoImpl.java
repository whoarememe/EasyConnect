package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.DeveloperDeviceDao;
import com.easyconnect.pojo.DeveloperDevice;

@Repository
public class DeveloperDeviceDaoImpl extends BaseDaoImpl implements DeveloperDeviceDao {

	@Override
	public DeveloperDevice getDeveloperDeviceById(int developerDeviceId) {
		String hql = "from DeveloperDevice as dd where dd.id=?";
		List<DeveloperDevice> developerDeviceList = (List<DeveloperDevice>)this.getHibernateTemplate().find(hql, developerDeviceId);
		
		if(null == developerDeviceList || 0 == developerDeviceList.size())
			return null;
		else
			return developerDeviceList.get(0);
	}
	
	@Override
	public List<DeveloperDevice> getDeveloperDeviceByDeveloperId(Integer developerId)
	{
		String hql = "from DeveloperDevice as dd where dd.developer.id=?";
		List<DeveloperDevice> developerDeviceList = (List<DeveloperDevice>)this.getHibernateTemplate().find(hql, developerId);
		
		if(null == developerDeviceList || 0 == developerDeviceList.size())
			return null;
		else
			return developerDeviceList;
	}
	
	@Override
	public DeveloperDevice getDeveloperDeviceByDeviceTypeIdAndModel(Integer deviceByDeviceTypeId,String model)
	{
		String hql = "from DeveloperDevice as dd where dd.deviceType.id=? and dd.model=?";
		List<DeveloperDevice> developerDeviceList = (List<DeveloperDevice>)this.getHibernateTemplate().find(hql, deviceByDeviceTypeId,model);
		
		if(null == developerDeviceList || 0 == developerDeviceList.size())
			return null;
		else
			return developerDeviceList.get(0);
	}

}
