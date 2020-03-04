package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.BusinessUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* (BusinessUser)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 16:28
*/
@Repository
@Mapper
public interface BusinessUserMapper {

    /**
     * 查询单个用户 通过id
     * @param userId
     * @return
     */
    BusinessUser getUserById(@Param("userId") String userId);


    /**
     * 添加用户
     * @param businessUser
     * @return
     */
    int addUser(BusinessUser businessUser);

    /**
     * 修改密码
     * @param userId
     * @param password
     * @return
     */
    int updatePw(@Param("userId") String userId, @Param("password") String password);

    /**
     * 修改用户角色
     * @param userId
     * @param userRole
     * @return
     */
    int updateRole(@Param("userId") String userId, @Param("userRole") String userRole);


}