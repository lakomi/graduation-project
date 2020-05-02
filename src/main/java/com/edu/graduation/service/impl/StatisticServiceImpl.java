package com.edu.graduation.service.impl;

import com.edu.graduation.dao.FoodMapper;
import com.edu.graduation.dao.SellMapper;
import com.edu.graduation.entity.bean.Food;
import com.edu.graduation.entity.bean.Sell;
import com.edu.graduation.entity.dto.SellTotalDTO;
import com.edu.graduation.enums.MessageEnum;
import com.edu.graduation.service.StatisticService;
import com.edu.graduation.utils.DateUtil;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.FoodEveryDaySellVo;
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
    @Autowired
    private FoodMapper foodMapper;

    @Override
    public ResultVo everyDaySellTotal(SellTotalDTO sellTotalDTO) {
        List<Sell> sellList = sellMapper.findAllByStoreIdAndTime(sellTotalDTO.getStoreId(), sellTotalDTO.getStartDay(), sellTotalDTO.getEndDay());
        Map<String, Double> map = getDateValueMaps(sellTotalDTO.getStartDay(), sellTotalDTO.getEndDay(), sellList);
        return ResultVoUtil.success(map);
    }

    @Override
    public ResultVo everyDayFoodSellCount(SellTotalDTO sellTotalDTO) {
        //获取商家的菜品列表
        List<Food> foods = foodMapper.getAllFoodFromOne(sellTotalDTO.getStoreId(), MessageEnum.FOOD_STATUS_ON.getCode());
        List<FoodEveryDaySellVo> foodEveryDaySellVoList = new ArrayList<>();

        //针对每一个菜品循环，查询其销售情况
        for (Food food : foods) {
            FoodEveryDaySellVo foodEveryDaySellVo = new FoodEveryDaySellVo();
            foodEveryDaySellVo.setFoodName(food.getFoodName());
            List<Sell> sells = sellMapper.findFoodSellBystoreIdAndTime(sellTotalDTO.getStoreId(), food.getFoodId(), sellTotalDTO.getStartDay(), sellTotalDTO.getEndDay());
            foodEveryDaySellVo.setDay_values(getDateValueMaps(sellTotalDTO.getStartDay(), sellTotalDTO.getEndDay(), sells));
            foodEveryDaySellVoList.add(foodEveryDaySellVo);
        }
        return ResultVoUtil.success(foodEveryDaySellVoList);
    }

    private Map<String, Double> getDateValueMaps(String startDay, String endDay, List<Sell> sellList) {
        Double daySum = 0.0;
        Date startday = DateUtil.convertStringTodate(startDay);
        Date endday = DateUtil.convertStringTodate(endDay);
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
        return map;
    }


}
