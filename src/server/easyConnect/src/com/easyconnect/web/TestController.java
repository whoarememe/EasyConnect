package com.easyconnect.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyconnect.pojo.Test;
import com.easyconnect.service.TestService;
import com.easyconnect.util.ResponseMapUtil;

@Controller
@RequestMapping(value = "/test")
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping(value = "/getAllTest")
	@ResponseBody
	public Map<String, Object> getAllClass() {
			
			List<Test> testList = testService.getTestInfo();
			
			if(testList == null) {
				return ResponseMapUtil.responseError(null);
			} else {
				return ResponseMapUtil.responseSuccess(testList);
			}
	}
}
