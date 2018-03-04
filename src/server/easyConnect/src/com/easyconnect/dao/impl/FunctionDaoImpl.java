package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.FunctionDao;
import com.easyconnect.pojo.Fucntion;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl implements FunctionDao {

	@Override
	public String getFunctionInfo(Integer functionId) {
		String hql = "from Fucntion as f where f.id=?";
		List<Fucntion> functionList = (List<Fucntion>)this.getHibernateTemplate().find(hql, functionId);
		String result;
		if(functionList != null && functionList.size() != 0)
		{
			result = functionList.get(0).getFunctions();
		}
		else
		{
			result = "not exists";
		}
		
		return result;
	}

}
