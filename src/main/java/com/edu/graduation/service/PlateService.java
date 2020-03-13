package com.edu.graduation.service;

import com.edu.graduation.vo.ResultVo;

public interface PlateService {
    /**
     * 获取某个商家的所有盘子信息
     * @param storeId
     * @return
     */
    ResultVo getAllPlates(Integer storeId);
}
