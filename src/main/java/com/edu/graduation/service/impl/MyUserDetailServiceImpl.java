package com.edu.graduation.service.impl;


import com.edu.graduation.dao.BusinessUserMapper;
import com.edu.graduation.entity.UserGrantedAuthority;
import com.edu.graduation.entity.bean.BusinessUser;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 返回用户名和密码及权限
 * @author q
 */
@Slf4j
@Component
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private BusinessUserMapper businessUserMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        //根据用户名查找用户信息
        BusinessUser businessUser = businessUserMapper.getUserById(userId);


        if(StringUtils.isEmpty(businessUser)){
            throw new MyException(MyExceptionEnum.USERID_NOT_EXIT);
        }
        String password = businessUser.getPassword();
        //GrantedAuthority是security提供的权限类，
        List<UserGrantedAuthority> grantedAuthorityList = new ArrayList<UserGrantedAuthority>();
        //获取该用户角色，并放入list中
        getRoles(businessUser,grantedAuthorityList);
        return new User(userId,password,grantedAuthorityList);
    }

    public void getRoles(BusinessUser userInfo,List<UserGrantedAuthority> list){
        for (String role:userInfo.getUserRole().split(",")) {
            //权限如果前缀是ROLE_，security就会认为这是个角色信息，而不是权限，例如ROLE_MENBER就是MENBER角色，CAN_SEND就是CAN_SEND权限
            list.add(new UserGrantedAuthority("ROLE_"+role));
        }
    }


}
