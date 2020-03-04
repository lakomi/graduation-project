package com.edu.graduation.utils;


import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author q
 */
@Slf4j
public class AuthenticationUtil {

    public static String getAuthUserId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authUserId = (String )authentication.getPrincipal();

        //token失效
        if (authUserId.equals(MyExceptionEnum.REPEAT_LOGIN.getMessage())){
            log.info("token 失效");
            throw new MyException(MyExceptionEnum.REPEAT_LOGIN);
        }
        //token为null(在JWT中已拦截。若token为null，则直接返回403)
        if (authUserId.equals(MyExceptionEnum.PLEASE_LOGIN_FIRST.getMessage())){
            throw new MyException(MyExceptionEnum.PLEASE_LOGIN_FIRST);
        }
        return authUserId;
    }

}
