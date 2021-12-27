package cn.rainspace.logistics.controller;

import cn.rainspace.logistics.entity.Chunk;
import cn.rainspace.logistics.entity.Staff;
import cn.rainspace.logistics.entity.User;
import cn.rainspace.logistics.service.AdminService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService service;

    @GetMapping("get-orders")
    public JSONObject getOrders() {
        return service.getOrders();
    }

    @GetMapping("get-users")
    public JSONObject getUsers() {
        return service.getUsers();
    }

    @GetMapping("get-staffs")
    public JSONObject getStaffs() {
        return service.getStaffs();
    }

    @GetMapping("get-chunks")
    public JSONObject getChunks() {
        return service.getChunks();
    }

    @PostMapping("add-user")
    public JSONObject addUser(@RequestBody JSONObject req) {
        return service.addUser(req.toJavaObject(User.class));
    }

    @PostMapping("edit-user")
    public JSONObject editUser(@RequestBody JSONObject req) {
        return service.editUser(req.toJavaObject(User.class));
    }

    @PostMapping("delete-user")
    public JSONObject deleteUser(@RequestBody JSONObject req) {
        return service.deleteUser(req.getIntValue("id"));
    }

    @PostMapping("add-chunk")
    public JSONObject addChunk(@RequestBody JSONObject req) {
        return service.addChunk(req.toJavaObject(Chunk.class), 0);
    }

    @PostMapping("edit-chunk")
    public JSONObject editChunk(@RequestBody JSONObject req) {
        return service.addChunk(req.toJavaObject(Chunk.class), 1);
    }

    @PostMapping("delete-chunk")
    public JSONObject deleteChunk(@RequestBody JSONObject req) {
        return service.deleteChunk(req.getIntValue("id"));
    }

    @PostMapping("add-staff")
    public JSONObject addStaff(@RequestBody JSONObject req) {
        return service.addStaff(req.toJavaObject(Staff.class), 0);
    }

    @PostMapping("edit-staff")
    public JSONObject editStaff(@RequestBody JSONObject req) {
        return service.addStaff(req.toJavaObject(Staff.class), 1);
    }

    @PostMapping("delete-staff")
    public JSONObject deleteStaff(@RequestBody JSONObject req) {
        return service.deleteStaff(req.getIntValue("id"));
    }

    @PostMapping("match")
    public JSONObject match(@RequestBody JSONObject req) {
        return service.match(req.getIntValue("orderId"), req.getIntValue("chunkId"), req.getIntValue("staffId"));
    }

    @PostMapping("arrival")
    public JSONObject arrival(@RequestBody JSONObject req) {
        return service.arrival(req.getIntValue("orderId"));
    }

    @PostMapping("delete-order")
    public JSONObject deleteOrder(@RequestBody JSONObject req) {
        return service.deleteOrder(req.getIntValue("orderId"));
    }

    @GetMapping("count")
    public JSONObject count() {
        return service.count();
    }
}
