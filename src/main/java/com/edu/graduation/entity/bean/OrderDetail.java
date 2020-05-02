package com.edu.graduation.entity.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (Orderdetail)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "orderDetail")
public class OrderDetail {

    
    @Id
    private String detailId;//10位

    
    private Integer storeId;

    
    private String orderId;

    
    private String foodId;

    
    private String userId;

    
    private Double price;

    
    private Integer count;

    
    private String dateDay;

    public OrderDetail() {
    }

    public OrderDetail(String detailId, Integer storeId, String orderId, String foodId, String userId, Double price, Integer count, String dateDay) {
        this.detailId = detailId;
        this.storeId = storeId;
        this.orderId = orderId;
        this.foodId = foodId;
        this.userId = userId;
        this.price = price;
        this.count = count;
        this.dateDay = dateDay;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}