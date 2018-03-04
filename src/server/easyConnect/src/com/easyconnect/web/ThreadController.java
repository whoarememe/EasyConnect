package com.easyconnect.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easyconnect.dao.DeveloperDao;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.dao.UserDeviceDao;
import com.easyconnect.service.DeviceService;
import com.easyconnect.service.HeartBeatService;
import com.easyconnect.service.UserService;
import com.easyconnect.thread.HeartThread;
import com.easyconnect.util.ConstUtil;

@Controller
@RequestMapping(value = "/thread")
public class ThreadController {
	
	@Autowired
	private UserDao userDaoImpl;
	@Autowired
	private HeartBeatService heartBeatServiceImpl;
	@Autowired
	private DeviceInUseDao deviceInUseDaoImpl;
	
	@Autowired
	private HeartBeatService heartBeatService;
	
	@Autowired
	private UserService userServiceImpl;
	
	@Autowired
	private DeveloperDao developerDaoImpl;
	
	@Autowired
	private UserDeviceDao userDeviceDaoImpl;
	
	@Autowired
	private DeviceService deviceServiceImpl;

	
	@RequestMapping(value = "/main")
	@ResponseBody
	public Map<String, Object> heart() {
		if (ConstUtil.isStart == false) {
			System.out.println("start");
			new HeartThread(heartBeatServiceImpl, deviceInUseDaoImpl, userServiceImpl, 
					developerDaoImpl, userDaoImpl, userDeviceDaoImpl, deviceServiceImpl).start();
		}
		return null;
	}
}
