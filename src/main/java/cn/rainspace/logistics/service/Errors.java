package cn.rainspace.logistics.service;

import com.alibaba.fastjson.JSONObject;

public class Errors {
    public static final JSONObject SUCCESS = createError(0,"操作成功");
    public static final JSONObject NOT_LOGGED_IN = createError(10,"请先登录再进行操作");
    public static final JSONObject WRONG_PASSWORD = createError(11,"密码错误");
    public static final JSONObject WRONG_EMAIL_FORMAT = createError(12,"邮箱格式错误");
    public static final JSONObject EMAIL_ALREADY_EXISTS = createError(13,"邮箱已存在");
    public static final JSONObject NAME_ALREADY_EXISTS = createError(14,"用户名已存在");
    public static final JSONObject VALIDATION_NOT_SENT = createError(15,"请先发送验证码");
    public static final JSONObject WRONG_VALIDATION = createError(16,"验证码错误");
    public static final JSONObject FREQUENT_VALIDATION = createError(17,"验证码发送过于频繁");
    public static final JSONObject EXPIRED_VALIDATION = createError(18,"验证码已过期");
    public static final JSONObject NOT_ADMIN = createError(19,"不具有管理员权限");
    public static final JSONObject NOT_RECEIVER = createError(20,"不是正确的收件人");
    public static final JSONObject USER_NOT_EXIST = createError(21,"该用户不存在");
    public static final JSONObject UNKNOWN_ERROR = createError(999,"未知错误");
    private static JSONObject createError(int status,String message){
        JSONObject error = new JSONObject();
        error.put("status",status);
        error.put("msg",message);
        return error;
    }
}
