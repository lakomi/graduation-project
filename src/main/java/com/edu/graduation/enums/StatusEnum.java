package com.edu.graduation.enums;

import lombok.Getter;

@Getter
public enum  StatusEnum {
        FOOD_STATUS_ON(1,"商品在架"),

        FOOD_STATUS_OFF(0,"商品下架"),

    ;
    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
