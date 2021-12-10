package cn.rainspace.logistics.service;

import cn.rainspace.logistics.repository.ContactDao;
import cn.rainspace.logistics.repository.OrderDao;
import cn.rainspace.logistics.repository.UserDao;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private UserDao userDao;

    public JSONObject getUsers(){
        JSONObject res = new JSONObject();
        res.put("users",userDao.getAll());
        res.put("status",0);
        return res;
    }

    public JSONObject getOrders(){
        JSONObject res = new JSONObject();
        res.put("orders",orderDao.getAll());
        res.put("status",0);
        return res;
    }
}
