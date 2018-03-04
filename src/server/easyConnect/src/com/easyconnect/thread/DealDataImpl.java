package com.easyconnect.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

import com.easyconnect.dao.DeveloperDao;
import com.easyconnect.dao.DeviceInUseDao;
import com.easyconnect.dao.UserDao;
import com.easyconnect.dao.UserDeviceDao;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.pojo.DeviceInUsing;
import com.easyconnect.service.DeviceService;
import com.easyconnect.service.HeartBeatService;
import com.easyconnect.service.UserService;

import net.sf.json.JSONObject;

public class DealDataImpl implements DealDataInterface {

	private HeartBeatService heartBeatServiceImpl;
	
	private DeviceInUseDao deviceInUseDaoImpl;
	
	private UserService userServiceImpl;
	
	private DeveloperDao developerDaoImpl;
	
	private UserDao userDaoImpl;
	
	private UserDeviceDao userDeviceDaoImpl;
	
	private DeviceService deviceServiceImpl;
	
	private JSONObject jsonObject;
		
	public DealDataImpl(	
			 HeartBeatService heartBeatServiceImpl,
			 DeviceInUseDao deviceInUseDaoImpl,
			 UserService userServiceImpl,
			 DeveloperDao developerDaoImpl,
			 UserDao userDaoImpl,
			 UserDeviceDao userDeviceDaoImpl,
			 DeviceService deviceServiceImpl) {
		
		System.out.println("in dealThread: init");
		
		jsonObject = new JSONObject();
		
		this.heartBeatServiceImpl = heartBeatServiceImpl;
		this.deviceInUseDaoImpl = deviceInUseDaoImpl;
		this.userServiceImpl = userServiceImpl;
		this.developerDaoImpl = developerDaoImpl;
		this.userDaoImpl = userDaoImpl;
		this.userDeviceDaoImpl = userDeviceDaoImpl;
		this.deviceServiceImpl = deviceServiceImpl;
	}

	@Override
	public String dealData(String jsonMsg, DatagramChannel dc, InetSocketAddress addr) {
		// TODO Auto-generated method stub
		jsonObject = JSONObject.fromObject(jsonMsg);
		System.out.println("recv json str -->" + jsonMsg);
		if (jsonObject.has("code")) {
			System.out.println("in dealThread: has code");

			switch (jsonObject.getInt("code")) {

				case 1:
				{
					System.out.println("in dealThread: heart pkt");

					switch (jsonObject.getInt("direction")) {
					case 1:
						System.out.println("in dealThread: user heart pkt");
						heartBeatServiceImpl.userHeartBeat(jsonObject.getInt("userId"), addr.getAddress().toString().replace("/",""), addr.getPort());
						break;
					case 2:
						System.out.println("in dealThread: device heart pkt");
						heartBeatServiceImpl.deviceHeartBeat(jsonObject.getInt("deviceId"), jsonObject.getInt("developerDeviceId"), 
								jsonObject.getString("password"), addr.getAddress().toString().replace("/",""), addr.getPort());
						break;

					default:
						break;
					}
					break;
				}

				case 2:
				{
					System.out.println("in dealThread: msg pkt");
					ByteBuffer b = ByteBuffer.allocate(1024);

					switch (jsonObject.getInt("direction")) {
					case 1: {
						System.out.println("in dealThread: user msg pkt");

						DeviceInUsing diu = deviceInUseDaoImpl.getDeviceByDeviceId(jsonObject.getInt("deviceId"));

						if (diu == null) {

							jsonObject.put("msg", "设备不存在");
							jsonObject.put("hasRead", 0);
							jsonObject.put("direction", 2);
							
							System.out.println("msg" + jsonObject.toString());
							b.put(jsonObject.toString().getBytes());  
							b.flip();
							try {
								dc.send(b, addr);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							if (diu.getState() == 1) {

								b.put(jsonMsg.getBytes());  
								b.flip();
								InetSocketAddress address = new InetSocketAddress(diu.getIp(), diu.getPort());
								try {
									System.out.println("准备发送");
									dc.send(b, address);
									System.out.println("发送成功");
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								userServiceImpl.sendMsgToDev(jsonObject.getInt("deviceId"), 1, 
										jsonObject.getInt("userId"), jsonObject.getInt("msgType"), 
										jsonObject.getString("msg"), jsonObject.getLong("time"));
								
							} else {
								System.out.println("设备不在线");
								jsonObject.put("msg", "device not online");
								jsonObject.put("hasRead", 0);
								jsonObject.put("direction", 2);
								
								b.put(jsonObject.toString().getBytes());  
								b.flip();
								try {
									dc.send(b, addr);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
						
						break;
					}

					case 2: {
						int userId = jsonObject.getInt("userId");
						int deviceId = jsonObject.getInt("deviceId");
						
						jsonObject.put("hasRead", 0);
						jsonObject.put("direction", 2);
						jsonObject.put("time", System.currentTimeMillis());
						
						if (userId == 0) {
							//
							List<AppUser> appUsers = userDeviceDaoImpl.getUsersByDeviceId(deviceId);
							if (appUsers == null) {
								System.out.println("app users null");
								deviceServiceImpl.sendMsgToApp(userId, deviceId, jsonObject.getInt("msgType"), 
										jsonObject.getString("msg"), jsonObject.getLong("time"), jsonObject.getInt("level"), 0);
							} else {
								System.out.println("user exist");
								int hasRead = 0;
								for (AppUser appUser : appUsers) {
									if (appUser.getState() == 1) {
										b = ByteBuffer.allocate(1024);
										System.out.println(appUser.getId() + "online-----------------");
										hasRead = 1;
										InetSocketAddress address = new InetSocketAddress(appUser.getIp(), appUser.getPort());
										b.put(jsonObject.toString().getBytes());  
										b.flip();
										try {
											dc.send(b, address);
										} catch (IOException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}
								
								deviceServiceImpl.sendMsgToApp(userId, deviceId, jsonObject.getInt("msgType"), 
										jsonObject.getString("msg"), jsonObject.getLong("time"), jsonObject.getInt("level"), hasRead);

							}
							
						} else {
							AppUser appUser = userDaoImpl.getUserByUserId(userId);
							InetSocketAddress address;
							
							if (appUser != null) {
								address = new InetSocketAddress(appUser.getIp(), appUser.getPort());
								if (appUser.getState() == 1) {
									System.out.println("user is online");
									
									address = new InetSocketAddress(appUser.getIp(), appUser.getPort());
									b.put(jsonObject.toString().getBytes());
									b.flip();
									try {
										dc.send(b, address);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									deviceServiceImpl.sendMsgToApp(userId, deviceId, jsonObject.getInt("msgType"), 
											jsonObject.getString("msg"), jsonObject.getLong("time"), jsonObject.getInt("level"), 1);
								} else {
									System.out.println("user not online");
									deviceServiceImpl.sendMsgToApp(userId, deviceId, jsonObject.getInt("msgType"), 
											jsonObject.getString("msg"), jsonObject.getLong("time"), jsonObject.getInt("level"), 0);
									
								}
							}
							
						}
						
						break;
					}
					
					default:
						break;
					}
					break;
				}
				default:
					break;
			}
		}
		
		return "helo test";
	}

}
