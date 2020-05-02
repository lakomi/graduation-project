package com.edu.graduation.vo;

import lombok.Data;

@Data
public class OrderVo {

    private String orderId;
    private Double total;
    private String dateDay;
    private String status;

    public OrderVo() {
    }

    public OrderVo(String orderId, Double total, String dateDay, String status) {
        this.orderId = orderId;
        this.total = total;
        this.dateDay = dateDay;
        this.status = status;
    }
}
