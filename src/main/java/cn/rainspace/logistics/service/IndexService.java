package cn.rainspace.logistics.service;

import cn.rainspace.logistics.entity.Order;
import cn.rainspace.logistics.repository.OrderDao;
import cn.rainspace.logistics.utils.email.MailSender;
import cn.rainspace.logistics.utils.email.VerifyCode;
import cn.rainspace.logistics.utils.email.message.VerificationMessage;
import com.alibaba.fastjson.JSONObject;
import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.repository.UserDao;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.servlet.http.HttpSession;

@Service
public class IndexService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrderDao orderDao;
	
	public JSONObject doLogin(String email, String password, HttpSession session) {
		// 最终返回的对象
	    JSONObject res = new JSONObject();
		User user = userDao.getByEmail(email);
	    if (user==null) {
			res.put("code", 1);
	        res.put("msg", "该账号不存在，请检查后重试");
	    }else if(!user.getPassword().equals(password)){
			res.put("code", 2);
	    	res.put("msg", "密码错误");
	    }else {
			res.put("code", 0);
			session.setAttribute("user", userDao.getByEmail(email));
			session.setMaxInactiveInterval(30*24*60);
		}
	    return res;
	}
	
	public JSONObject doRegister(User user, String verifyCode, HttpSession session) {
		JSONObject res = new JSONObject();
		if(userDao.getByName(user.getUsername())!=null){
			res.put("code",1);
			res.put("msg","用户名已被注册");
		}else if(userDao.getByEmail(user.getEmail())!=null){
			res.put("code",2);
			res.put("msg","邮箱已被注册");
		}else if(VerifyCode.get(user.getEmail())==null){
			res.put("code",3);
			res.put("msg","请先发送验证码");
		}else if(!VerifyCode.get(user.getEmail()).equals(verifyCode)){
			res.put("code",4);
			res.put("msg","验证码错误");
		}else {
			userDao.add(user);
			session.setAttribute("user", userDao.getByEmail(user.getEmail()));
			session.setMaxInactiveInterval(30*24*60);
			res.put("code",0);
		}
	    return res;
	}

	private boolean sendEmail(Message msg) throws Exception{
		MailSender sender =new MailSender();
		sender.init();
		sender.sendMessage(msg);
		sender.close();
		return true;
	}

	public JSONObject checkEmail(String email){
		JSONObject res = new JSONObject();
		res.put("code",0);
		String code = VerifyCode.set(email);
		try {
			sendEmail(VerificationMessage.generate(email,code));
		} catch (SMTPAddressFailedException e){
			res.put("code",1);
			res.put("msg","邮箱地址格式错误");
		} catch (Exception e) {
			e.printStackTrace();
			res.put("code",2);
			res.put("msg","未知错误");
		}
		return res;
	}

	public JSONObject getOrders(String type, User user){
		JSONObject res = new JSONObject();
		res.put("code",0);
		if(type.equals("send")) {
			res.put("orders", orderDao.getBySenderId(user.getId()));
		} else {
			res.put("orders", orderDao.getByReceiverId(user.getId()));
		}
		return res;
	}

	public JSONObject addOrder(Order order, User user){
		JSONObject res = new JSONObject();
		order.setSenderId(user.getId());
		orderDao.add(order);
		return res;
	}
}
