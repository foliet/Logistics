package cn.rainspace.logistics.service;

import cn.rainspace.logistics.entity.*;
import cn.rainspace.logistics.repository.*;
import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class AdminService {
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private ContactDao contactDao;
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

    public JSONObject addUser(User user, int type){
        if(type==0) {
            userDao.add(user);
        }else{
            userDao.update(user);
        }
        return Errors.SUCCESS;
    }

    public JSONObject deleteUser(int id){
        userDao.delete(id);
        return Errors.SUCCESS;
    }

    public JSONObject addChunk(Chunk chunk, int type){
        if(type==0) {
            chunkDao.add(chunk);
        }else{
            chunkDao.update(chunk);
        }
        return Errors.SUCCESS;
    }

    public JSONObject deleteChunk(int id){
        chunkDao.delete(id);
        return Errors.SUCCESS;
    }

    public JSONObject addStaff(Staff staff, int type){
        if(type==0) {
            staffDao.add(staff);
        }else{
            staffDao.update(staff);
        }
        return Errors.SUCCESS;
    }

    public JSONObject deleteStaff(int id){
        staffDao.delete(id);
        return Errors.SUCCESS;
    }

    public JSONObject match(int orderId,int chunkId,int staffId){
        Chunk chunk = chunkDao.getById(chunkId);
        chunk.setStatus(1);
        chunkDao.update(chunk);
        Staff staff = staffDao.getById(staffId);
        staff.setStatus(1);
        staffDao.update(staff);
        Order order = orderDao.getById(orderId);
        order.setChunkId(chunkId);
        order.setStaffId(staffId);
        order.setStatus(1);
        orderDao.update(order);
        User sender = userDao.getById(contactDao.getById(order.getSenderContactId()).getReceiverId());
        sender.setUnreadMessage(sender.getUnreadMessage()+1);
        userDao.update(sender);
        User receiver = userDao.getById(contactDao.getById(order.getReceiverContactId()).getReceiverId());
        receiver.setUnreadMessage(receiver.getUnreadMessage()+1);
        userDao.update(receiver);
        Notice notice = new Notice();
        notice.setOwnerId(sender.getId());
        notice.setTitle("您寄出的物品已出仓");
        notice.setContent("您寄给"+receiver.getUsername()+"的物品已经从仓库发出，订单号"+orderId+"，将会被尽快送达。");
        noticeDao.add(notice);
        notice.setOwnerId(receiver.getId());
        notice.setTitle("您有一件新的物品在路上");
        notice.setContent(sender.getUsername()+"向您寄出了一件物品，订单号"+orderId+"，请注意查收。");
        noticeDao.add(notice);
        return Errors.SUCCESS;
    }

    public JSONObject arrival(int orderId){
        Order order = orderDao.getById(orderId);
        order.setStatus(2);
        orderDao.update(order);
        Chunk chunk = chunkDao.getById(order.getChunkId());
        chunk.setStatus(0);
        chunkDao.update(chunk);
        Staff staff = staffDao.getById(order.getStaffId());
        staff.setStatus(0);
        staffDao.update(staff);
        User sender = userDao.getById(contactDao.getById(order.getSenderContactId()).getReceiverId());
        sender.setUnreadMessage(sender.getUnreadMessage()+1);
        userDao.update(sender);
        User receiver = userDao.getById(contactDao.getById(order.getReceiverContactId()).getReceiverId());
        receiver.setUnreadMessage(receiver.getUnreadMessage()+1);
        userDao.update(receiver);
        Notice notice = new Notice();
        notice.setOwnerId(sender.getId());
        notice.setTitle("您寄出的物品已送达");
        notice.setContent("您寄给"+receiver.getUsername()+"的物品送达，订单号"+orderId+"，请提醒对方确认收货。");
        noticeDao.add(notice);
        notice.setOwnerId(receiver.getId());
        notice.setTitle("您有一件新的物品已送达");
        notice.setContent(sender.getUsername()+"向您寄出了的物品已送达，订单号"+orderId+"，请尽快确认收货。");
        noticeDao.add(notice);
        return Errors.SUCCESS;
    }

    public JSONObject deleteOrder(int id){
        orderDao.delete(id);
        return Errors.SUCCESS;
    }

    public JSONObject count(){
        JSONObject res = new JSONObject();
        LocalDate localDate = LocalDate.now();
        List<User>users = userDao.getAll();
        List<Order>orders = orderDao.getAll();
        ArrayList<String> dates = new ArrayList<>();
        ArrayList<Integer>userIncrease = new ArrayList<>();
        ArrayList<Integer>orderIncrease = new ArrayList<>();
        for(int i=0;i<7;i++){
            dates.add(localDate.getMonthValue()+"-"+localDate.getDayOfMonth());
            int x=0,y=0;
            for(User user:users){
                if(user.getCreateAt().toLocalDateTime().toLocalDate().equals(localDate))x++;
            }
            for(Order order:orders){
                if(order.getCreateAt().toLocalDateTime().toLocalDate().equals(localDate))y++;
            }
            userIncrease.add(x);
            orderIncrease.add(y);
            localDate=localDate.minusDays(1);
        }
        Collections.reverse(dates);
        Collections.reverse(userIncrease);
        Collections.reverse(orderIncrease);
        res.put("dates",dates);
        res.put("userIncrease",userIncrease);
        res.put("orderIncrease",orderIncrease);
        return res;
    }
}
