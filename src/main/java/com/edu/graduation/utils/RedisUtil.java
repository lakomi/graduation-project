package com.edu.graduation.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    private final RedisTemplate<String,String> redisTemplate;

    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 有效期30天
    private static long LAST_TIME = 30;


    /**
     * get值
     * @param key
     * @return
     */
    public String get(final String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key,String value){
        boolean result = false;
        try{
            redisTemplate.opsForValue().set(key,value,LAST_TIME, TimeUnit.DAYS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
