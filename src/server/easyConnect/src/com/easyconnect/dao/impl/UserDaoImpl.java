package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.UserDao;
import com.easyconnect.pojo.AppUser;

@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	@Override
	public AppUser getUserByTelePhone(String phone) {
		
		// TODO Auto-generated method stub
		
		String hql = "from AppUser as user where user.phone = ? ";
		List<AppUser> user = (List<AppUser>)this.getHibernateTemplate().find(hql,phone);
		AppUser myUser;
		if(user.size()==0)
		{
			myUser = null;
		}else
		{
			myUser = user.get(0);
		}
		return myUser;
		
	}
	
	@Override
	public AppUser getUserByUserId(Integer userId){
		
		String hql = "from AppUser as user where user.id = ? ";
		List<AppUser> user = (List<AppUser>)this.getHibernateTemplate().find(hql,userId);
		AppUser myUser;
		if(user.size()==0)
		{
			myUser = null;
		}else
		{
			myUser = user.get(0);
		}
		return myUser;
		
	}

	@Override
	public List<AppUser> getAppUsersOnline() {
		// TODO Auto-generated method stub
		String hqlString = "from AppUser as au where au.state = 1";
		List<AppUser> appUsers = (List<AppUser>)this.getHibernateTemplate().find(hqlString);
		if (hqlString == null || appUsers.size() == 0) return null;
		else return appUsers;
	}

}
