package com.easyconnect.thread;

import com.easyconnect.dao.DeveloperDao;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.dao.UserDeviceDao;
import com.easyconnect.service.DeviceService;
import com.easyconnect.service.HeartBeatService;
import com.easyconnect.service.UserService;
import com.easyconnect.socket.NioUdp;
import com.easyconnect.util.ConstUtil;

public class HeartThread extends Thread{	
	private NioUdp nudp;
	private DetectThread detectt;
	
	private HeartBeatService heartBeatServiceImpl;
	
	private DeviceInUseDao deviceInUseDaoImpl;
	
	private UserService userServiceImpl;
	
	private DeveloperDao developerDaoImpl;
	
	private UserDao userDaoImpl;
	
	private UserDeviceDao userDeviceDaoImpl;
	
	private DeviceService deviceServiceImpl;
	
	public HeartThread(HeartBeatService heartBeatServiceImpl,
			 DeviceInUseDao deviceInUseDaoImpl,
			 UserService userServiceImpl,
			 DeveloperDao developerDaoImpl,
			 UserDao userDaoImpl,
			 UserDeviceDao userDeviceDaoImpl,
			 DeviceService deviceServiceImpl) {	

		System.out.println("in heart thread: init");
		nudp = new NioUdp(ConstUtil.port);
		detectt = new DetectThread(userDaoImpl,  deviceInUseDaoImpl,  heartBeatServiceImpl);
		
		this.heartBeatServiceImpl = heartBeatServiceImpl;
		this.deviceInUseDaoImpl = deviceInUseDaoImpl;
		this.userServiceImpl = userServiceImpl;
		this.developerDaoImpl = developerDaoImpl;
		this.userDaoImpl = userDaoImpl;
		this.userDeviceDaoImpl = userDeviceDaoImpl;
		this.deviceServiceImpl = deviceServiceImpl;
	}
	
	@Override
	public void run() {
		System.out.println("in heart thread: run");
		detectt.start();
		nudp.start(new DealDataImpl( heartBeatServiceImpl,
				  deviceInUseDaoImpl,
				  userServiceImpl,
				  developerDaoImpl,
				  userDaoImpl,
				  userDeviceDaoImpl,
				  deviceServiceImpl
				  ));
		System.out.println("in heart thread: finish run");
	}

}
