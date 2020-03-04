package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.Plate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* (Plate)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 13:5
*/
@Repository
@Mapper
public interface PlateMapper{

    Plate getPlateById(@Param("plateId") String plateId);

    /**
     * 修改盘子数量信息
     * @param plateId
     * @param usedCount
     * @return
     */
    int modifyPlateUsedCount(@Param("plateId")String plateId,@Param("usedCount")Integer usedCount);

    /**
     * 获取某个盘子的被使用数量
     * @return
     */
    Integer getUsedCountById(@Param("plateId")String plateId);

    /**
     * 获取某个盘子对应的价格
     * @param plateId
     * @return
     */
    Double getPriceById(@Param("plateId")String plateId);




}