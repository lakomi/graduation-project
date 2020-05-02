package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AddFoodDTO {

    @NotBlank(message = "菜品名称不能为空")
    private String foodName;

    private String plateId;

    @NotNull(message = "商家编号不能为空")
    private Integer storeId;
}
