package cn.rainspace.logistics.service;

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
import java.util.List;

@Service
public class IndexService {
	@Autowired
	private UserDao dao;
	
	public JSONObject doLogin(String email, String password) {
		// 最终返回的对象
	    JSONObject res = new JSONObject();
		List<User>users = dao.listUserByEmail(email);
	    if (users.isEmpty()) {
			res.put("code", 1);
	        res.put("msg", "该账号不存在，请检查后重试");
	    }else if(!users.get(0).getPassword().equals(password)){
			res.put("code", 2);
	    	res.put("msg", "密码错误");
	    }else {
			res.put("code", 0);
			res.put("id",users.get(0).getId());
		}
	    return res;
	}
	
	public JSONObject doRegister(User user, String verifyCode) {
		JSONObject res = new JSONObject();
		if(!dao.listUserByName(user.getUsername()).isEmpty()){
			res.put("code",1);
			res.put("msg","用户名已被注册");
		}else if(!dao.listUserByEmail(user.getEmail()).isEmpty()){
			res.put("code",2);
			res.put("msg","邮箱已被注册");
		}else if(VerifyCode.get(user.getEmail())==null){
			res.put("code",3);
			res.put("msg","请先发送验证码");
		}else if(!VerifyCode.get(user.getEmail()).equals(verifyCode)){
			res.put("code",4);
			res.put("msg","验证码错误");
		}else {
			dao.addUser(user);
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
		res.put("code",0);
		return res;
	}
}
