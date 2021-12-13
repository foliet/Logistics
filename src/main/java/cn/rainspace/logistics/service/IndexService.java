package cn.rainspace.logistics.service;

import cn.rainspace.logistics.entity.Contact;
import cn.rainspace.logistics.entity.Order;
import cn.rainspace.logistics.repository.ContactDao;
import cn.rainspace.logistics.repository.NoticeDao;
import cn.rainspace.logistics.repository.OrderDao;
import cn.rainspace.logistics.utils.email.MailSender;
import cn.rainspace.logistics.utils.email.message.VerificationMessage;
import com.alibaba.fastjson.JSONObject;
import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.repository.UserDao;
import com.sun.mail.smtp.SMTPAddressFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Service
public class IndexService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private NoticeDao noticeDao;
	
	public JSONObject doLogin(String email, String password, HttpSession session) {
		// 最终返回的对象
	    JSONObject res = new JSONObject();
		User user = userDao.getByEmail(email);
	    if (user==null||!user.getPassword().equals(password)) {
			return Errors.WRONG_PASSWORD;
	    } else {
			res.put("status", 0);
			session.setAttribute("user", userDao.getByEmail(email));
			session.setMaxInactiveInterval(30*24*60);
		}
	    return res;
	}
	
	public JSONObject doRegister(User user, String verifyCode, HttpSession session) {
		JSONObject res = new JSONObject();
		if(userDao.getByName(user.getUsername())!=null){
			return Errors.NAME_ALREADY_EXISTS;
		}else if(userDao.getByEmail(user.getEmail())!=null){
			return Errors.EMAIL_ALREADY_EXISTS;
		}else if(session.getAttribute("verifyCode")==null){
			return Errors.VALIDATION_NOT_SENT;
		}else if(!session.getAttribute("verifyCode").equals(verifyCode)){
			return Errors.WRONG_VALIDATION;
		}else if(System.currentTimeMillis()-(Long) session.getAttribute("verifyCodeCreateTime")>1000*60*5){
			return Errors.EXPIRED_VALIDATION;
		}else {
			userDao.add(user);
			session.setAttribute("user", userDao.getByEmail(user.getEmail()));
			session.setMaxInactiveInterval(30*24*60);
			res.put("status",0);
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

	public JSONObject checkEmail(String email,HttpSession session){
		JSONObject res = new JSONObject();
		if(session.getAttribute("verifyCodeCreateTime")!=null&&System.currentTimeMillis()-(Long)session.getAttribute("verifyCodeCreateTime")<1000*60){
			return Errors.FREQUENT_VALIDATION;
		}
		String verifyCode = String.valueOf(new Random().nextInt(1000,10000));
		try {
			sendEmail(VerificationMessage.generate(email,verifyCode));
		} catch (SMTPAddressFailedException e){
			return Errors.WRONG_EMAIL_FORMAT;
		} catch (Exception e) {
			e.printStackTrace();
			return Errors.UNKNOWN_ERROR;
		}
		res.put("status",0);
		session.setAttribute("verifyCode",verifyCode);
		session.setAttribute("verifyCodeCreateTime",System.currentTimeMillis());
		return res;
	}

	public JSONObject getOrders(String type, User user){
		JSONObject res = new JSONObject();
		res.put("status",0);
		if(type.equals("send")) {
			res.put("orders", orderDao.getBySenderId(user.getId()));
		} else {
			res.put("orders", orderDao.getByReceiverId(user.getId()));
		}
		return res;
	}

	public JSONObject addOrder(Order order){
		JSONObject res = new JSONObject();
		orderDao.add(order);
		res.put("status",0);
		return res;
	}
	
	public JSONObject getNotices(User user){
		JSONObject res = new JSONObject();
		res.put("status",0);
		res.put("notices",noticeDao.getByOwnerId(user.getId()));
		return res;
	}

	public JSONObject getContacts(User user){
		JSONObject res = new JSONObject();
		res.put("status",0);
		res.put("contacts",contactDao.getByOwnerId(user.getId()));
		return res;
	}

	public JSONObject addContact(Contact contact){
		JSONObject res = new JSONObject();
		contactDao.add(contact);
		res.put("status",0);
		return res;
	}
}
