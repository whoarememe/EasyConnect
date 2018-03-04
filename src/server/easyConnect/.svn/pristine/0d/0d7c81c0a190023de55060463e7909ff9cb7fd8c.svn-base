package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.DeveloperDao;
import com.easyconnect.pojo.Developer;

@Repository
public class DeveloperDaoImpl extends BaseDaoImpl implements DeveloperDao {

	@Override
	public Developer getDeveloperById(int developerId) {
		String hql = "from Developer as d where d.id=?";
		List<Developer> developerList = (List<Developer>)this.getHibernateTemplate().find(hql, developerId);
		
		if(null == developerList || 0 == developerList.size())
			return null;
		else
			return developerList.get(0);
	}
	
	@Override
	public Developer getDeveloperByPhone(String phone)
	{
		String hql = "from Developer as d where d.phone=?";
		List<Developer> developerList = (List<Developer>)this.getHibernateTemplate().find(hql, phone);
		
		if(null == developerList || 0 == developerList.size())
			return null;
		else
			return developerList.get(0);
		
	}

}
