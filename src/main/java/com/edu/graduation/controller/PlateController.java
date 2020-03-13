package com.edu.graduation.controller;

import com.edu.graduation.service.PlateService;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plate")
@CrossOrigin
@Slf4j
public class PlateController {

    @Autowired
    private PlateService plateService;

    @GetMapping("/getAllPlates")
    public ResultVo getAllPlates(@RequestParam("storeId") Integer storeId){
        return plateService.getAllPlates(storeId);
    }



}
