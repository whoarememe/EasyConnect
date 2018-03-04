package com.easyconnect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.MessageDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.DeviceInUsing;
import com.easyconnect.pojo.Message;
import com.easyconnect.service.DeviceService;

@Service
public class DeviceServiceImpl implements DeviceService{
	
	/**autowired**/
	@Autowired
	private MessageDao msgDao;
	
	@Autowired
	private DeviceInUseDao deviceInUseDaoImpl;
	
	@Autowired
	private UserDao userDaoImpl;

	@Override
	public Message getDeviceUnreadMsg(Integer deviceId) {
		
		System.out.println(deviceId);
		// TODO Auto-generated method stub
		Message msg = msgDao.getDeviceUnreadMsg(deviceId);
		
		if (msg == null) return null;
		
		msg.setHasread(1);
		msgDao.update(msg);
		
		return msg;
	}

	@Override
	public boolean sendMsgToApp(Integer userId, Integer deviceId,
			Integer msgType, String msg, Long time, Integer level,
			int hasRead) {
		// TODO Auto-generated method stub
		
		AppUser appUser = userDaoImpl.getUserByUserId(userId);
		DeviceInUsing deviceInUsing = deviceInUseDaoImpl.getDeviceByDeviceId(deviceId);
		
		if (appUser != null && deviceInUsing != null) {
			Message message = new Message(appUser, deviceInUsing, 2, msgType, msg, time, hasRead, level);
			msgDao.add(message);
			return true;
		}
		
		return false;
	}



}
