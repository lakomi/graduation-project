package com.edu.graduation.handler;


import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName SignInExceptionHandler
 * @Description 捕获 SignInException 异常
 * @Author q
 * @Date 18-7-22 下午3:14
 */
@Slf4j
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(MyException.class)
    public ResultVo handlerSignInException(MyException e){
        ResultVo resultVo = null;
        resultVo = ResultVoUtil.error(CodeEnum.ERROR.getCode(),e.getMessage());
        return resultVo;
    }



}
