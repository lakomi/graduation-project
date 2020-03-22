package com.edu.graduation.entity.bean;

import lombok.Data;
import com.alibaba.fastjson.JSON;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * (Plate)表实体类
 *
 * @author qiu
 * @version 1.0
 * @since 2020/2/22 12:18
 */
@Data
@Table(name = "plate")
public class Plate {

    
    @Id
    private String plateId;//4位

    private Double price;

    private Integer usedCount;
    
    private String remark;

    private Integer storeId;

    private String picture;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}