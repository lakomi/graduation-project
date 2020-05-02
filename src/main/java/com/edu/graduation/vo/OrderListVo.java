package com.edu.graduation.vo;

import lombok.Data;

import java.util.List;

@Data
public class OrderListVo {
    private String storeName;

    private List<OrderVo> orders;

    public OrderListVo() {
    }

    public OrderListVo(String storeName, List<OrderVo> orders) {
        this.storeName = storeName;
        this.orders = orders;
    }
}
