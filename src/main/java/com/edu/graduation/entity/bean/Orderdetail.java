package com.edu.graduation.entity.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * (Orderdetail)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "orderDetail")
public class Orderdetail {

    
    @Id
    private String detailId;//10位

    
    private Integer storeId;

    
    private String orderId;

    
    private String foodId;

    
    private String userId;

    
    private Double price;

    
    private Integer count;

    
    private Date date;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}