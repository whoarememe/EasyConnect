package com.easyconnect.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.easyconnect.bean.UserDataBean;
import com.easyconnect.bean.UserUnreadMsgBean;
import com.easyconnect.pojo.Message;
import com.easyconnect.pojo.AppUser;
import com.easyconnect.service.UserService;
import com.easyconnect.util.ResponseMapUtil;

@Controller
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/userLogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userLogin(String phone,String password) {
			
			AppUser user = userService.userLogin(phone, password);
			
			if(user == null) {
				return ResponseMapUtil.responseError("");
			} else {
				UserDataBean bean = new UserDataBean(user.getId(), user.getName(), user.getPhone(), user.getMail());
				return ResponseMapUtil.responseSuccess(bean);
			}
	}
	
	@RequestMapping(value = "/userRegister",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> userResgister(String phone, String name, String password,String mail) {
		
		AppUser user = userService.userRegister(phone, name, password, mail);
		
		if(user == null)
		{
			return ResponseMapUtil.responseError(null);
		}
		else
		{
			return ResponseMapUtil.responseSuccess(null);
		}

	}
	
	
	
	@RequestMapping(value = "/getUserUnreadMsg",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getUserUnreadMsg(Integer userId)
	{
		List<Message> messages = userService.getUserUnreadMsg(userId);
		

			if(messages.size() !=0)
			{	
				List<UserUnreadMsgBean> userUnreadMsg = new ArrayList<UserUnreadMsgBean>();
				
				for(int i=0;i<messages.size();i++)
				{
					Message message = messages.get(i);
					UserUnreadMsgBean userUnreadMsgBean = new UserUnreadMsgBean(message.getDirection(), message.getId()
							,message.getAppUser().getId(), message.getMsgType(), message.getMsg(), message.getTime(), 0,message.getLevel());
					userUnreadMsg.add(userUnreadMsgBean);
				}
				
				return ResponseMapUtil.responseSuccess(userUnreadMsg);
			}
			else
			{
				return ResponseMapUtil.responseError(null);
			}
		
	}
	

	@RequestMapping(value = "/sendMsgToDev",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> sendMsgToDev(Integer deviceId,Integer direction,Integer userId,Integer msgType,String msg,Long time)
	{
		boolean result  = userService.sendMsgToDev(deviceId, direction, userId, msgType, msg, time);
		if(result)
		{
			return ResponseMapUtil.responseSuccessMess("");
		}
		else
		{
			return ResponseMapUtil.responseError("device is off-line");
		}
	}
	
	@RequestMapping(value = "/modifyPassword",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> modifyPassword(Integer userId,String password,String newPassword)
	{
		boolean result  = userService.modifyPassword(userId, password, newPassword);
		
		if(result)
		{
			return ResponseMapUtil.responseSuccess(null);
		}
		else
		{
			return ResponseMapUtil.responseError(null);
		}
	}
	
}