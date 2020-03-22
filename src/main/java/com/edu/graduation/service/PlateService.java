package com.edu.graduation.service;

import com.edu.graduation.entity.dto.AddPlateDTO;
import com.edu.graduation.entity.dto.ModifyPlateDTO;
import com.edu.graduation.vo.ResultVo;

public interface PlateService {
    /**
     * 获取某个商家的所有盘子信息
     * @param storeId
     * @return
     */
    ResultVo getAllPlates(Integer storeId);

    /**
     * 删除某个盘子
     * @param plateId
     * @return
     */
    ResultVo deletePlate(String plateId);

    /**
     * 修改盘子信息，单价/备注
     * @param modifyPlateDTO
     * @return
     */
    ResultVo modifyPlate(ModifyPlateDTO modifyPlateDTO);

    /**
     * 添加盘子
     * @param addPlateDTO
     * @return
     */
    ResultVo addPlate(AddPlateDTO addPlateDTO);
}
