package com.edu.graduation.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginDTO
 * @Description 登录信息
 * @Author q
 * @Date 18-7-21 下午6:14
 */
@Data
public class LoginDTO {

    @NotBlank(message = "用户名不能为空")
    private String userId;

    @NotBlank(message = "密码不能为空")
    private String password;
}
