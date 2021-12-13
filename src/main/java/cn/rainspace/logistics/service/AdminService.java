package cn.rainspace.logistics.service;

import cn.rainspace.logistics.repository.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private ChunkDao chunkDao;
    @Autowired
    private StaffDao staffDao;
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

    public JSONObject getStaffs(){
        JSONObject res = new JSONObject();
        res.put("staffs",staffDao.getAll());
        res.put("status",0);
        return res;
    }

    public JSONObject getChunks(){
        JSONObject res = new JSONObject();
        res.put("chunks",chunkDao.getAll());
        res.put("status",0);
        return res;
    }
}
