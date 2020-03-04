package com.edu.graduation.service.impl;

import com.edu.graduation.dao.BusinessUserMapper;
import com.edu.graduation.entity.bean.BusinessUser;
import com.edu.graduation.entity.dto.LoginDTO;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.filter.JWTLoginFilter;
import com.edu.graduation.filter.UserAuthenticationProvider;
import com.edu.graduation.service.UserService;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author q
 * @Date 18-7-21 下午6:15
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * 管理员身份
     */
    private final String ADMIN_ROLE = "A";

    @Autowired
    private BusinessUserMapper businessUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserAuthenticationProvider userAuthenticationProvider;

    private JWTLoginFilter jwtLoginFilter = new JWTLoginFilter();


    /**
     * 商家登录
     * @param loginDTO
     * @param response
     * @return
     */
    @Override
    public ResultVo loginR(LoginDTO loginDTO, HttpServletResponse response) {
        log.info("进入UserServiceImpl的loginR");
        //解析请求,从request中取出authentication
        Authentication authentication = jwtLoginFilter.attemptAuthentication(loginDTO);
        //验证用户名和密码
        Authentication resultAuthentication = userAuthenticationProvider.authenticate(authentication);
        BusinessUser user = businessUserMapper.getUserById(loginDTO.getUserId());
        if (!user.getUserRole().contains(ADMIN_ROLE)) {
            throw new MyException(MyExceptionEnum.USERID_NOT_EXIT);
        }
        //验证通过，生成token，放入response
        ResultVo resultVo = jwtLoginFilter.successfulAuthentication(resultAuthentication, response, user.getUserId());
        return resultVo;
    }


    @Override
    public ResultVo loginT(LoginDTO loginDTO, HttpServletResponse response) {

        log.info("进入UserBasicInfoServiceImpl的userLogin");
        //解析请求,从request中取出authentication
        Authentication authentication = jwtLoginFilter.attemptAuthentication(loginDTO);
        //验证用户名和密码
        Authentication resultAuthentication = userAuthenticationProvider.authenticate(authentication);
        /* 查找用户名，返回 */
        String userId = businessUserMapper.getUserById(loginDTO.getUserId()).getUserId();
        //验证通过，生成token，放入response
        ResultVo resultVo = jwtLoginFilter.successfulAuthentication(resultAuthentication, response, userId);
        return resultVo;
    }






}
