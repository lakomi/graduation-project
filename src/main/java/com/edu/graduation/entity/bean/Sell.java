package com.edu.graduation.entity.bean;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * (Sell)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "sell")
public class Sell {

    
    @Id
    private String sellId;//自动生成  6位

    
    private String foodId;

    
    private Integer sellCount;

    
    private Double price;

    
    private Double total;

    
    private Date date;

    private Integer storeId;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}