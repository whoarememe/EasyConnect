package com.easyconnect.dao;

import com.easyconnect.pojo.Developer;

public interface DeveloperDao extends BaseDao {

	public Developer getDeveloperById(int developerId);
	
	public Developer getDeveloperByPhone(String phone);
}
