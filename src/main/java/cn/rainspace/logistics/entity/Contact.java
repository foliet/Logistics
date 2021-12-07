package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Contact {
    int id;
    int ownerId;
    String province;
    String city;
    String district;
    String telephone;
    String address;
    Timestamp createAt;
}
