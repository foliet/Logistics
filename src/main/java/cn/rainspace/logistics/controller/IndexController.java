package cn.rainspace.logistics.controller;

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
		return service.checkEmail(email);
	}

	@GetMapping("/get-orders")
	public  JSONObject getOrders(String type, @SessionAttribute User user) {
		return service.getOrders(type,user);
	}

	@PostMapping("/add-order")
	public  JSONObject addOrder(@RequestBody JSONObject req, @SessionAttribute User user) {
		return service.addOrder(req.toJavaObject(Order.class),user);
	}
}
