package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.Food;
import com.edu.graduation.vo.FoodManageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* (Food)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 13:5
*/
@Repository
@Mapper
public interface FoodMapper {
    /***
     * 查找所有菜品
     * @param storeId
     * @return
     */
    List<Food> getAllFoodFromOne(@Param("storeId")Integer storeId);

    List<FoodManageVo> getAllFood(@Param("storeId")Integer storeId,@Param("status")Integer status);

    Food getOneById(@Param("foodId")String foodId);

    /**
     * 添加菜品
     * @param food
     * @return
     */
    int addFood(@Param("food") Food food);

    /**
     * 修改菜品信息
     * @param food
     * @return
     */
    int updateFood(@Param("food") Food food);

    /**
     * 删除菜品
     * @param foodId
     * @return
     */
    int deleteFood(@Param("foodId")String foodId, @Param("status")Integer status);


}