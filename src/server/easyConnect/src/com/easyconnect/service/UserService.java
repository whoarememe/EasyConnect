package com.easyconnect.service;

import java.util.List;

import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.Message;

public interface UserService {
	
	public AppUser userLogin(String phone,String password);
	
	public AppUser userRegister(String phone,String name,String password,String mail);
	
	public List<Message> getUserUnreadMsg(Integer userId);

	public boolean sendMsgToDev(Integer deviceId,Integer direction,Integer userId,Integer msgType,String msg,Long time);
	
	public boolean modifyPassword(Integer userId,String password,String newPassword);
	

}
