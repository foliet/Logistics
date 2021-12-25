package cn.rainspace.logistics.controller;

import cn.rainspace.logistics.entity.Contact;
import cn.rainspace.logistics.entity.Order;
import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.service.IndexService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class IndexController {
	@Autowired
	private IndexService service;
	@Autowired
	private HttpServletRequest request;

	@GetMapping("/logout")
	public JSONObject logout(){
		JSONObject res = new JSONObject();
		request.getSession().invalidate();
		res.put("status",0);
		return res;
	}

	@PostMapping("/login")
	public JSONObject login(@RequestBody JSONObject req) {
		return service.doLogin(req.getString("email"), req.getString("password"),request.getSession());
	}
	
	@PostMapping("/register")
	public JSONObject register(@RequestBody JSONObject req) {
		return service.doRegister(req.toJavaObject(User.class),req.getString("verifyCode"),request.getSession());
	}

	@GetMapping("/check-email")
	public JSONObject checkEmail(String email){
		return service.checkEmail(email,request.getSession());
	}

	@GetMapping("/get-orders")
	public JSONObject getOrders(String type, @SessionAttribute User user) {
		return service.getOrders(type,user);
	}

	@PostMapping("/add-order")
	public JSONObject addOrder(@RequestBody JSONObject req) {
		return service.addOrder(req.toJavaObject(Order.class));
	}

	@GetMapping("/get-notices")
	public JSONObject getNotices(@SessionAttribute User user) {
		return service.getNotices(user);
	}

	@PostMapping("/add-contact")
	public JSONObject addContact(@RequestBody JSONObject req,@SessionAttribute User user) {
		Contact contact = req.toJavaObject(Contact.class);
		contact.setOwnerId(user.getId());
		return service.addContact(contact,0);
	}

	@PostMapping("/edit-contact")
	public JSONObject editContact(@RequestBody JSONObject req,@SessionAttribute User user) {
		Contact contact = req.toJavaObject(Contact.class);
		contact.setOwnerId(user.getId());
		return service.addContact(contact,1);
	}

	@PostMapping("/delete-contact")
	public JSONObject deleteContact(@RequestBody JSONObject req) {
		return service.deleteContact(req.getIntValue("id"));
	}

	@GetMapping("/get-contacts")
	public JSONObject getContacts(@SessionAttribute User user,String type){
		return service.getContacts(user,type);
	}

	@GetMapping("/get-user")
	public JSONObject getUser(@SessionAttribute User user){
		return service.getUser(user.getId());
	}

	@PostMapping("edit-password")
	public JSONObject editPassword(@RequestBody JSONObject req,@SessionAttribute User user){
		return service.editPassword(req.getString("oldpsd"),req.getString("newpsd"),user);
	}

	@PostMapping("take-goods")
	public JSONObject takeGoods(@RequestBody JSONObject req,@SessionAttribute User user){
		return service.takeGoods(req.getIntValue("orderId"),user);
	}
}
