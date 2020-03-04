package com.edu.graduation.entity.bean;

import lombok.Data;
import com.alibaba.fastjson.JSON;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (BusinessUser)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 16:28
 */
@Data
@Table(name = "business_user")
public class BusinessUser {

    
    @Id
    private String userId;

    
    private String password;

    
    private String userRole;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}