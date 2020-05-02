package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ModifyFoodDTO {

    @NotBlank(message = "菜品编号不能为空")
    private String foodId;

    @NotBlank(message = "菜品名称不能为空")
    private String foodName;

    private String plateId;

}
