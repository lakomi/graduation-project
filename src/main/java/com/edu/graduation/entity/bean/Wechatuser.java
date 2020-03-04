package com.edu.graduation.entity.bean;

import lombok.Data;
import com.alibaba.fastjson.JSON;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (Wechatuser)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "wechatUser")
public class Wechatuser {

    
    @Id
    private String userId;

    
    private String userName;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}