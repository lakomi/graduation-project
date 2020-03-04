package com.edu.graduation.exception;


import com.edu.graduation.enums.MyExceptionEnum;
import lombok.Data;

/**
 * @ClassName SignInException
 * @Description TODO
 * @Author q
 * @Date 18-7-21 下午5:59
 */
@Data
public class MyException extends RuntimeException {
    private Integer code;

    public MyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public MyException(MyExceptionEnum signInExceptionEnum) {
        super(signInExceptionEnum.getMessage());
        this.code = signInExceptionEnum.getCode();
    }




}
