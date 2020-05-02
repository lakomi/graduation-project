package com.edu.graduation.enums;

import lombok.Getter;

@Getter
public enum MessageEnum {
    FOOD_STATUS_ON(1, "商品在架"),
    FOOD_STATUS_OFF(0, "商品下架"),
    ADD_CUSTOMIZE_URL(2, "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/dish/add"),
    SEARCH_CUSTOMIZE_URL(3, "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/dish/search"),
    DELETE_CUSTOMIZE_URL(4, "https://aip.baidubce.com/rest/2.0/image-classify/v1/realtime_search/dish/delete"),
    APP_ID(5, "18453210"),
    ORDER_DETAIL_ID(10,"orderDetail中的主键的长度"),
    ;
    private Integer code;

    private String message;

    MessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
