package com.edu.graduation.vo;

import lombok.Data;

@Data
public class PlateVo {

    private String plateId;//4位

    private Double price;

    private Object picture;

    private Integer usedCount;

    private String remark;


}
