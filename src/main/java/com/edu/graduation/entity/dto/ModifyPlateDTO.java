package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ModifyPlateDTO {
    @NotBlank(message = "盘子编号不能为空")
    private String plateId;
    @NotNull(message = "单价不能为空")
    private Double price;
    @NotBlank(message = "备注不能为空")
    private String remark;
}
