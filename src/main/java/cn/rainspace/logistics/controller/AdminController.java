package cn.rainspace.logistics.controller;

import cn.rainspace.logistics.service.AdminService;
import cn.rainspace.logistics.service.IndexService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService service;

    @GetMapping("get-orders")
    public JSONObject getOrders(){
        return service.getOrders();
    }

    @GetMapping("get-users")
    public JSONObject getUsers(){
        return service.getUsers();
    }
}
