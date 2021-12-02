package cn.rainspace.logistics.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
	private String username;
	private String password;
	private int id;
	private String email;
	private int group_id;
	private Timestamp create_at;
}
