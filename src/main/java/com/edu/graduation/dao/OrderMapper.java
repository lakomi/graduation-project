package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* (Order)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 13:5
*/
@Repository
@Mapper
public interface OrderMapper{
    /**
     * 删除某菜品的订单信息
     * @param foodId
     * @return
     */
    int deleteByFoodId(@Param("foodId")String foodId);

    /**
     * 插入一条order记录
     * @param order
     * @return
     */
    int insertOrder(@Param("order")Order order);

    /**
     * 修改订单状态
     * @param orderId
     * @param status
     * @return
     */
    int updateState(@Param("orderId")String orderId,@Param("status")Integer status);

    /**
     * 查询某个微信用户的订单列表
     * @return
     */
    List<Order> getListByUserid(@Param("userId")String userId);

}