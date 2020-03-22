package com.edu.graduation.controller;


import com.edu.graduation.entity.dto.SellTotalDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.StatisticService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RequestMapping("/sta")
@RestController
public class StatisticController {

    @Autowired
    private StatisticService statisticService;


    @PostMapping("/getEverydaySellcount")
    public ResultVo getEverydaySellcount(@Valid SellTotalDTO sellTotalDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("/addPlate @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return statisticService.everyDaySellTotal(sellTotalDTO);
    }


}
