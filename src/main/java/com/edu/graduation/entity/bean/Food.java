package com.edu.graduation.entity.bean;

import lombok.Data;
import com.alibaba.fastjson.JSON;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (Food)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "food")
public class Food {

    
    @Id
    private String foodId;//5位

    
    private String foodName;

    
    private Double foodPrice;

    
    private Integer storeId;

    
    private String plateId;


    private Integer foodStatus;//1为在架，0为下架

   @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}