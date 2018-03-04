package com.easyconnect.web;

import java.util.Map;

import com.easyconnect.bean.DeviceUnreadMsgBean;
import com.easyconnect.pojo.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyconnect.service.DeviceService;
import com.easyconnect.util.ResponseMapUtil;

@Controller
@RequestMapping(value = "/device")
public class DeviceController {
	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping(value = "/getMsg",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getDeviceUnreadMsg(Integer deviceId, String password, Integer developerId,
			String key, String deviceType) {
		System.out.println("----------------------------");
		System.out.println(deviceId);
		Message msg = deviceService.getDeviceUnreadMsg(deviceId);
		
		if (msg != null) {
			DeviceUnreadMsgBean deviceUnreadMsgBean = new DeviceUnreadMsgBean(msg.getMsg(), msg.getMsgType(), 
					msg.getAppUser().getId(), 123123);
			
			return ResponseMapUtil.responseSuccess(deviceUnreadMsgBean);
		} else {
			return ResponseMapUtil.responseError("");
		}
		
	}
}
