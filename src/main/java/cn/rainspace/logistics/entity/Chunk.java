package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Chunk {
    int id;
    Timestamp createAt;
    String number;
    String model;
    String place;
    int status;
}
