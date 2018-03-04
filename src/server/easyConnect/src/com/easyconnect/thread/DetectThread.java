package com.easyconnect.thread;

import java.util.List;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.DeviceInUsing;
import com.easyconnect.service.HeartBeatService;

public class DetectThread extends Thread{

	private DeviceInUseDao deviceInUseDaoImpl;
	private UserDao userDaoImpl;
	private HeartBeatService heartBeatServiceImpl;
	
	private List<AppUser> appUsers;
	private List<DeviceInUsing> deviceInUsings;
	
	public DetectThread(UserDao userDao, DeviceInUseDao deviceInUseDao, HeartBeatService heartBeatService) {
		// TODO Auto-generated constructor stub
		System.out.println("in detectThread: init");
		this.userDaoImpl = userDao;
		this.deviceInUseDaoImpl = deviceInUseDao;
		this.heartBeatServiceImpl = heartBeatService;
	}
	
	@Override
	public void run() {
		while (true) {
			System.out.println("in detectThread: run");
			appUsers = userDaoImpl.getAppUsersOnline();
			deviceInUsings = deviceInUseDaoImpl.getDevicesOnline();
			
			if (appUsers == null || deviceInUsings == null) {
				try {
					Thread.sleep(300000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			long t = System.currentTimeMillis();
			
			for (DeviceInUsing deviceInUsing : deviceInUsings) {
				try {
					if (t > deviceInUsing.getHeartTime() + 300000) {
						System.out.println("in detectThread device : " + deviceInUsing.getDeviceId() + "offline");
						heartBeatServiceImpl.deviceOffline(deviceInUsing.getDeviceId());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
			for (AppUser appUser : appUsers) {
				try {
					if (t > appUser.getHeartTime() + 300000) {
						System.out.println("in detectThread user : " + appUser.getId() + "offline");
						heartBeatServiceImpl.userOffline(appUser.getId());
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			System.out.println("refresh state");
			
			try {
				Thread.sleep(300000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
