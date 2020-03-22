package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddPlateDTO {

    @NotNull(message = "店铺编号不能为空")
    private Integer storeId;

    @NotNull(message = "单价不能为空")
    private Double price;

    @NotBlank(message = "备注不能为空")
    private String remark;

    @NotBlank(message = "图片编码不能为空")
    private String picture;
}
