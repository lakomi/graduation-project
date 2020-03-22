package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SellTotalDTO {
    @NotNull(message = "店铺编号不得为空")
    private Integer storeId;
    @NotBlank(message = "起始日期不得为空")
    private String startDay;
    @NotBlank(message = "结束日期不得为空")
    private String endDay;
}
