package com.edu.graduation.service;

import com.edu.graduation.entity.dto.SellTotalDTO;
import com.edu.graduation.vo.ResultVo;

public interface StatisticService {
    /**
     * 获取店铺每天的营业额
     * @param sellTotalDTO
     * @return
     */
    ResultVo everyDaySellTotal(SellTotalDTO sellTotalDTO);

    ResultVo everyDayFoodSellCount(SellTotalDTO sellTotalDTO);

}
