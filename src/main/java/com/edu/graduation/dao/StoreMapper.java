package com.edu.graduation.dao;

import com.edu.graduation.entity.bean.Store;
import com.edu.graduation.entity.dto.ModifySelfDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* (Store)表Mapper类
*
* @author qiu
* @version 1.0
* @since 2020/2/22 13:5
*/
@Repository
@Mapper
public interface StoreMapper {
    /**
     * 由userId获取商家的个人信息
     * @param userId
     * @return
     */
    Store getOneByUserId(@Param("userId")String userId);

    /**
     * 修改店铺信息
     * @param modifySelfDTO
     * @return
     */
    int updateAllData(@Param("modifySelfDTO") ModifySelfDTO modifySelfDTO);

}