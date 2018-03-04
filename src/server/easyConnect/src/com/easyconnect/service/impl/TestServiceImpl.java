package com.easyconnect.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyconnect.dao.TestDao;
import com.easyconnect.pojo.Test;
import com.easyconnect.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;

	@Override
	public List<Test> getTestInfo() {

		return testDao.getTestInfo();
	}
	
	
}
