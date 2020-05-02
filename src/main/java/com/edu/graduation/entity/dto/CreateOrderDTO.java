package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateOrderDTO {

    @NotBlank(message = "微信用户不能为空")
    private String userId;

    @NotNull(message = "店铺编号不能为空")
    private Integer storeId;

    private List<String> plateIdList;

}
