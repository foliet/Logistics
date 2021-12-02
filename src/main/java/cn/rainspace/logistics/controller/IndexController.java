package cn.rainspace.logistics.controller;

import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.service.IndexService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class IndexController {
	@Autowired
	private IndexService service;

	
	@PostMapping("/login")
	public JSONObject login(@RequestBody JSONObject req) {
		return service.doLogin(req.getString("email"), req.getString("password"));
	}
	
	@PostMapping("/register")
	public JSONObject register(@RequestBody JSONObject req) {
		return service.doRegister(req.toJavaObject(User.class),req.getString("verifyCode"));
	}

	@GetMapping("/check-email")
	public JSONObject checkEmail(String email){
		return service.checkEmail(email);
	}
}
