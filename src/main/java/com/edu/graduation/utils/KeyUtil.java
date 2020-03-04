package com.edu.graduation.utils;

import java.util.Random;

/**
 * @ClassName KeyUtil
 * @Description 生成签到表主键编号
 * @Author q
 * @Date 18-7-22 下午3:17
 */
public class KeyUtil {

    private static final String POOL = "AaBbCcDdEeFfGgHhIiJjKLMmNnOPQqRrSTUVWXYyZ0123456789";


    /**
     * 生成 length 长的随机数
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(POOL.length());
            sb.append(POOL.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 生成主键
     * 时间+3位随机数
     * @return
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(90)+10;
        return System.currentTimeMillis()+String.valueOf(number);
    }


}
