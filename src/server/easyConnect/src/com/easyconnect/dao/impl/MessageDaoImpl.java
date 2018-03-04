package com.easyconnect.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.easyconnect.dao.MessageDao;
import com.easyconnect.pojo.Message;

@Repository
public class MessageDaoImpl extends BaseDaoImpl implements MessageDao {
	
	@Override
	public List<Message> getUserUnreadMsg(Integer userId)
	{
		String hql = "from Message as message where message.appUser.id = ? and message.hasread=0 and message.direction=2";
		List<Message> messageList = (List<Message>)this.getHibernateTemplate().find(hql,userId);

		return messageList;
	}

	@Override
	public Message getDeviceUnreadMsg(Integer deviceId) {
		// TODO Auto-generated method stub
		String hql = "from Message as message where message.deviceInUsing.deviceId=? and message.hasread=0 and message.direction=1 ORDER BY message.time";
		
		System.out.println(deviceId);
		@SuppressWarnings("unchecked")
		List<Message> message = (List<Message>)this.getHibernateTemplate().find(hql, deviceId);
		System.out.print(message.size());
		if (message.size() <= 0) {
			return null;
		} else {
			return message.get(0);
		}
	}

}
