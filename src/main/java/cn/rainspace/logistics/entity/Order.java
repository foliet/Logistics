package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    Timestamp createAt;
    int id;
    int senderContactId;
    String senderName;
    int receiverContactId;
    String receiverName;
    String remark;
    String title;
    float value;
    float weight;
    float volume;
    int chunkId;
    int staffId;
    int status;
}
