package com.easyconnect.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.MessageDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.dao.UserDeviceDao;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.DeviceInUsing;
import com.easyconnect.pojo.Message;
import com.easyconnect.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private DeviceInUseDao deviceInUseDao;
	
	@Autowired
	private UserDeviceDao userDeviceDao;

	@Override
	public AppUser userLogin(String phone, String password) {
		// TODO Auto-generated method stub
		AppUser user = userDao.getUserByTelePhone(phone);
		if(user == null)
		{
			
		}
		else if(!user.getPassword().equals(password))
		{
			user = null;
		}
		else
		{
			user.setState(1);
			userDao.update(user);
		}
		return user;
	}

	@Override
	public AppUser userRegister(String phone, String name, String password,
			String mail) {
		// TODO Auto-generated method stub
		AppUser user = userDao.getUserByTelePhone(phone);
		if(user == null)
		{
			AppUser myUser = new AppUser(name, password, phone, mail,0);
			userDao.add(myUser);
			return myUser;
		}

		return null;
	}

	@Override
	public List<Message> getUserUnreadMsg(Integer userId) {
		// TODO Auto-generated method stub
		List<Message> messages = messageDao.getUserUnreadMsg(userId);
		if(messages.size() !=0)
		{
			for(int i=0;i<messages.size();i++)
			{
				messages.get(i).setHasread(1);
				messageDao.update(messages.get(i));
			}
		}
		return messages;
	}

	
	
	@Override
	public boolean sendMsgToDev(Integer deviceId,Integer direction,Integer userId,Integer msgType,String msg,Long time)
	{
		DeviceInUsing deviceInUsing = deviceInUseDao.getDeviceByDeviceId(deviceId);
		
		AppUser user = userDao.getUserByUserId(userId);
		
		if(deviceInUsing.getState() ==1)
		{
			Message message = new Message(user,deviceInUsing,direction,msgType,msg,time,0,0);			
			messageDao.add(message);
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean modifyPassword(Integer userId,String password,String newPassword)
	{
		AppUser user = userDao.getUserByUserId(userId);
		if(user.getPassword().equals(password))
		{
			user.setPassword(newPassword);
			userDao.update(user);
			return true;
		}
		else
		{
			return false;
		}
		
	}

}
