package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    Timestamp createAt;
    int id;
    int senderId;
    int SenderContactId;
    int receiverId;
    int receiverContactId;
    String desc;
    String title;
    int value;
    int weight;
    int volume;
    int status;
    int chunkId;
    int driverId;
}
