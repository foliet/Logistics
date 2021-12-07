package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Driver {
    int id;
    Timestamp createAt;
    String name;
    String gender;
    int status;
}
