package com.edu.graduation.vo;

import lombok.Data;

@Data
public class SelectStoreVo {
    private Integer storeId;

    private String address;

    public SelectStoreVo(Integer storeId, String address) {
        this.storeId = storeId;
        this.address = address;
    }
}
