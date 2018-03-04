package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.UserDeviceDao;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.UserDevice;

@Repository
public class UserDeviceDaoImpl extends BaseDaoImpl implements UserDeviceDao {

	@Override
	public List<UserDevice> getUserDevice(int userId) {
		String hql = "from UserDevice as ud where ud.appUser.id=?";
		List<UserDevice> userDeviceList = (List<UserDevice>)this.getHibernateTemplate().find(hql, userId);
		
		if(null == userDeviceList || 0 == userDeviceList.size())
			return null;
		else
			return userDeviceList;
		
	}

	@Override
	public List<AppUser> getUsersByDeviceId(int deviceId) {
		// TODO Auto-generated method stub
		String hqlString = "select ud.appUser from UserDevice as ud where ud.deviceInUsing.deviceId = ?";
		List<AppUser> appUsers = (List<AppUser>)this.getHibernateTemplate().find(hqlString, deviceId);
		
		if (appUsers == null || appUsers.size() == 0) {
			return null;
		} else {
			return appUsers;
		}
	}
	
	@Override
	public UserDevice getgetUserDeviceByUserIdAndDeviceId(int userId,int deviceId)
	{
		String hql = "from UserDevice as ud where ud.deviceInUsing.deviceId = ? and ud.appUser.id = ?";
		List<UserDevice> userDevice = (List<UserDevice>)this.getHibernateTemplate().find(hql, deviceId,userId);
		
		if (userDevice == null || userDevice.size() == 0) {
			return null;
		} else {
			return userDevice.get(0);
		}

	}

}
