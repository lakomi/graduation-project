package com.edu.graduation.entity.bean;

import lombok.Data;
import com.alibaba.fastjson.JSON;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (Store)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "store")
public class Store {

    
    @Id
    private Integer storeId;

    
    private String storeName;

    
    private String storeAddress;

    
    private String storeOwner;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}