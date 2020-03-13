package com.edu.graduation.enums;

import lombok.Getter;

@Getter
public enum MyExceptionEnum {
    /**
     * token过期 需要重新登录
     */
    REPEAT_LOGIN(2, "请重新登录"),
    /**
     * token为空 先登录
     */
    PLEASE_LOGIN_FIRST(16, "请先登录"),
    /**
     * 用户名不存在
     */
    USERID_NOT_EXIT(1, "用户名不存在"),
    /**
     * UserAuthenticationProvider 中提取用户详情出错
     */
    GET_USERDETAILS_ERROR(3, "登录中获取用户详情出错"),
    /**
     * 密码错误
     */
    PASSWORD_ERROR(9, "密码错误"),

    OLD_PASSWORD_ERROR(10, "原密码错误"),
    /**
     * 服务器内部错误，数据库操作出错
     */
    SQL_ERROR(4, "操作失败，请重试"),
    /**
     * 添加用户，判断是否存在
     */
    USERID_HAS_EXIT(6, "该用户已存在"),;



    private Integer code;

    private String message;

    MyExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
