package com.edu.graduation.service;

import com.edu.graduation.entity.dto.AddFoodDTO;
import com.edu.graduation.entity.dto.ModifyFoodDTO;
import com.edu.graduation.vo.ResultVo;

public interface FoodService {

    /**
     * 获取菜品列表
     *
     * @param storeId
     * @return
     */
    ResultVo getAllFoodList(Integer storeId);

    /**
     * 修改菜品信息
     *
     * @param modifyFoodDTO
     * @return
     */
    ResultVo modifyFood(ModifyFoodDTO modifyFoodDTO);

    /**
     * 添加菜品信息
     *
     * @param addFoodDTO
     * @return
     */
    ResultVo addFood(AddFoodDTO addFoodDTO);

    /**
     * 删除菜皮
     *
     * @param foodId
     * @return
     */
    ResultVo deleteFood(String foodId);

    /**
     * 获取某一菜品具体信息
     * @param foodId
     * @return
     */
    ResultVo getOneFood(String foodId);

}
