package com.easyconnect.dao;

import java.util.List;

import com.easyconnect.pojo.AppUser;

public interface UserDao extends BaseDao {
	
	public AppUser getUserByTelePhone(String phone);
	
	public AppUser getUserByUserId(Integer userId);	
	
	public List<AppUser> getAppUsersOnline();
}