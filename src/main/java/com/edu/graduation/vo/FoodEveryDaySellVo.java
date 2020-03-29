package com.edu.graduation.vo;

import lombok.Data;

import java.util.Map;

@Data
public class FoodEveryDaySellVo {

    private String foodName;

    private Map<String,Double> day_values;
}
