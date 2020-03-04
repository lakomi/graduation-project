package com.edu.graduation.filter;


import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.impl.MyUserDetailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 身份验证  用户名与密码是否正确
 * @author q
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private MyUserDetailServiceImpl myUserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //获取用户传来用户名和密码
        String userId = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("[登录]:  userId={}，password={}",userId,password);
        //根据用户名获取数据库中的用户名及密码
        UserDetails userDetails = myUserDetailService.loadUserByUsername(userId);
        if (StringUtils.isEmpty(userDetails)){
            throw new MyException(MyExceptionEnum.GET_USERDETAILS_ERROR);
        }
        /*验证密码与数据库中的加密密码是否匹配*/
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new MyException(MyExceptionEnum.PASSWORD_ERROR);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken(userId,password,userDetails.getAuthorities());
        return auth;
    }
    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }



}
