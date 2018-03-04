package com.easyconnect.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easyconnect.dao.FunctionDao;
import com.easyconnect.service.FunctionService;

@Service
public class FunctionServiceImpl implements FunctionService {
	@Autowired
	private FunctionDao functionDao;

	@Override
	public String getFunctionInfo(Integer functionId) {
		return functionDao.getFunctionInfo(functionId);
	}
}
