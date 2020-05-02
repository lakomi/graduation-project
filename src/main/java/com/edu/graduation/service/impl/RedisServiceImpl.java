package com.edu.graduation.service.impl;

import com.edu.graduation.AI.BaiduImageSearch;
import com.edu.graduation.service.RedisService;
import com.edu.graduation.utils.RedisUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RedisServiceImpl implements RedisService {
    private static String ACCESS_TOKEN_CLIENT = "access_token_qiu";

    private final RedisUtil redisUtil;

    public RedisServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public String getAuthValue() {
        String accessToken = redisUtil.get(ACCESS_TOKEN_CLIENT);
        //redis中没有,向百度发请求获取,并存入redis中
        if (StringUtils.isEmpty(accessToken)){
            accessToken = BaiduImageSearch.getAuth();
            redisUtil.set(ACCESS_TOKEN_CLIENT,accessToken);
        }
        return accessToken;
    }


}
