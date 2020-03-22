package com.edu.graduation.service.impl;

import com.edu.graduation.dao.SellMapper;
import com.edu.graduation.entity.bean.Sell;
import com.edu.graduation.entity.dto.SellTotalDTO;
import com.edu.graduation.service.StatisticService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private SellMapper sellMapper;

    @Override
    public ResultVo everyDaySellTotal(SellTotalDTO sellTotalDTO) {
        List<Sell> sellList = sellMapper.findAllByStoreIdAndTime(sellTotalDTO.getStoreId(), sellTotalDTO.getStartDay(), sellTotalDTO.getEndDay());
        Double daySum = 0.0;
        String day = sellTotalDTO.getStartDay();
        Map<String, Double> map = new TreeMap<>();
        for (Sell sell : sellList) {
            //同一天的销量情况
            if (day.equals(sell.getDateDay().toString())) {
                daySum += sell.getTotal();
            } else {
                //将前一个的日期-营业额放入map中
                map.put(day, daySum);
                // 日期变为新的一个，营业额为此条信息的total
                day = sell.getDateDay();
                daySum = sell.getTotal();
            }
        }
        map.put(day, daySum);


        return ResultVoUtil.success(map);
    }
}
