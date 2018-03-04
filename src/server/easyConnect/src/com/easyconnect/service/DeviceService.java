package com.easyconnect.service;

import com.easyconnect.pojo.Message;

public interface DeviceService {
	public Message getDeviceUnreadMsg(Integer deviceId);
	
	public boolean sendMsgToApp(Integer userId, Integer deviceId, 
			Integer msgType, String msg, Long time, Integer level, int hasRead);

}
