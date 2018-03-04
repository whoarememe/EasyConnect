package com.easyconnect.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyconnect.service.FunctionService;
import com.easyconnect.util.ResponseMapUtil;

@Controller
@RequestMapping(value = "/function")
public class FunctionController {
	@Autowired
	private FunctionService functionService;
	
	@RequestMapping(value = "getDevFunc")
	@ResponseBody
	public Map<String, Object> getFunctionInfo(@RequestParam Integer functionId)
	{
		String result = functionService.getFunctionInfo(functionId);
		return ResponseMapUtil.responseSuccess(result);
	}

}
