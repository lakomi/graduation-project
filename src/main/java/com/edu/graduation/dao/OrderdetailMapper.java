package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* (Orderdetail)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 13:5
*/
@Repository
@Mapper
public interface OrderdetailMapper {
    /**
     * 向表中查插入一条记录
     * @param orderDetail
     * @return
     */
    int addOrderDetail(@Param("orderDetail") OrderDetail orderDetail);

}