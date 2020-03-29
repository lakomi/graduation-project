package com.edu.graduation.service.impl;

import com.edu.graduation.dao.SellMapper;
import com.edu.graduation.entity.bean.Sell;
import com.edu.graduation.entity.dto.SellTotalDTO;
import com.edu.graduation.service.StatisticService;
import com.edu.graduation.utils.DateUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private SellMapper sellMapper;

    @Override
    public ResultVo everyDaySellTotal(SellTotalDTO sellTotalDTO) {
        List<Sell> sellList = sellMapper.findAllByStoreIdAndTime(sellTotalDTO.getStoreId(), sellTotalDTO.getStartDay(), sellTotalDTO.getEndDay());
        Double daySum = 0.0;
        Date startday = DateUtil.convertStringTodate(sellTotalDTO.getStartDay());
        Date endday = DateUtil.convertStringTodate(sellTotalDTO.getEndDay());
        Date day = startday;
        Map<String, Double> map = new TreeMap<>();
        // 初始化map
        while (day.getTime() >= startday.getTime() && day.getTime() <= endday.getTime()) {
            map.put(DateUtil.date2string(day), daySum);
            day = DateUtil.addDay(day);
        }
        //遍历查询结果，挨个累加销售额
        for (Sell sell : sellList) {
            map.put(sell.getDateDay(), map.get(sell.getDateDay()) + sell.getTotal());
        }
        return ResultVoUtil.success(map);
    }

    @Override
    public ResultVo everyDayFoodSellCount(SellTotalDTO sellTotalDTO) {
        List<Sell> sellList = sellMapper.findFoodSellBystoreIdAndTime(sellTotalDTO.getStoreId(),sellTotalDTO.getStartDay(),sellTotalDTO.getEndDay());


        return null;
    }


}
