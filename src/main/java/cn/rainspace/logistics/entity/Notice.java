package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Notice {
    int id;
    String title;
    String content;
    String ownerId;
    Timestamp createAt;
}
