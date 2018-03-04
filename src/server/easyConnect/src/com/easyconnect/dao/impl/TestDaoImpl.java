package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.pojo.Test;
import com.easyconnect.dao.TestDao;

@Repository
public class TestDaoImpl extends BaseDaoImpl implements TestDao {

	@Override
	public List<Test> getTestInfo() {
		// TODO Auto-generated method stub
		String hql = "from Test as t";
		List<Test> testList = (List<Test>) this.getHibernateTemplate().find(hql);
		return testList;
	}

}
