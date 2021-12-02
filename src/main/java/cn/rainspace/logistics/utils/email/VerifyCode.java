package cn.rainspace.logistics.utils.email;

import java.util.HashMap;
import java.util.Random;

public class VerifyCode {
    private static Random random = new Random();
    private static HashMap<String,String> map = new HashMap<>();
    public static String get(String email){
        return map.get(email);
    }
    public static String set(String email){
        String code = String.valueOf(random.nextInt(1000,10000));
        map.put(email, code);
        return code;
    }
}
