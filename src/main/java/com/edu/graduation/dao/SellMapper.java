package com.edu.graduation.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* (Sell)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 13:5
*/
@Repository
@Mapper
public interface SellMapper {

    /**
     * 删除某个菜品的记录
     * @param foodId
     * @return
     */
    int deleteByFoodId(@Param("foodId")String foodId);

}