package com.edu.graduation.entity;

import lombok.Data;

/**
 * 向第三方上传图片时携带的内容
 */
@Data
public class Brief {
    private Double name;
    private String id;

    public Brief(Double price, String id) {
        this.name = price;
        this.id = id;
    }

    public Brief() {}

    @Override
    public String toString() {
        return "{name:"+name+", id:"+id+"}";
    }
}
