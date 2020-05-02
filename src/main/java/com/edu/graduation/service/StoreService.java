package com.edu.graduation.service;

import com.edu.graduation.entity.dto.ModifySelfDTO;
import com.edu.graduation.vo.ResultVo;

public interface StoreService {

    /**
     * 获取商家个人信息
     * @return
     */
    ResultVo getStoreData(String userId);

    /**
     * 修改密码
     * @param userId
     * @return
     */
    ResultVo modifyPassword(String userId,String newPassword,String oldPassword);

    /**
     * 修改店铺信息
     * @param modifySelfDTO
     * @return
     */
    ResultVo modifyData(ModifySelfDTO modifySelfDTO);

    /**
     * 小程序  获取所有店铺信息
     * @return
     */
    ResultVo getAllStores();
}
