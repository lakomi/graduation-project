package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ModifySelfDTO {

    @NotNull(message = "店铺编号不能为空")
    private Integer storeId;//1,2,3...

    @NotBlank(message = "店铺名称不能为空")
    private String storeName;

    @NotBlank(message = "店铺地址不能为空")
    private String storeAddress;

    @NotBlank(message = "店铺所有者不能为空")
    private String storeOwner;

}
