package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.Sell;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    /**
     * 按照storeId查找某范围内的销售信息
     * @param storeId
     * @param startDay
     * @param endDay
     * @return
     */
    List<Sell> findAllByStoreIdAndTime(@Param("storeId")Integer storeId, @Param("startDay")String startDay,@Param("endDay")String endDay);

    /**
     * 查找某店铺中的菜品的销售信息
     * @param storeId
     * @param startDay
     * @param endDay
     * @return
     */
    List<Sell> findFoodSellBystoreIdAndTime(@Param("storeId")Integer storeId,@Param("startDay")String startDay,@Param("endDay")String endDay);


}