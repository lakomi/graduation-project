package com.edu.graduation.entity;

import lombok.Data;

import java.util.List;

@Data
public class DistinguishResult {
    private String log_id;

    private Integer result_num;

    private List<LocationList> result;

    private String error_code;

    private String error_msg;

}
