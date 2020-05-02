package com.edu.graduation.entity;

import lombok.Data;

import java.util.List;

@Data
public class LocationList {

    private Location location;

    private List<Dish> dishes;

}
