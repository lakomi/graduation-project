package com.edu.graduation.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FoodEveryDaySellVo {

    private String foodName;

    private List<Map<String,Double>> day_value_list;
}
