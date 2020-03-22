package com.edu.graduation.controller;

import com.edu.graduation.entity.dto.AddPlateDTO;
import com.edu.graduation.entity.dto.ModifyPlateDTO;
import com.edu.graduation.enums.CodeEnum;
import com.edu.graduation.service.PlateService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/plate")
@CrossOrigin
@Slf4j
public class PlateController {

    @Autowired
    private PlateService plateService;

    @GetMapping("/getAllPlates")
    public ResultVo getAllPlates(@RequestParam("storeId") Integer storeId) {
        return plateService.getAllPlates(storeId);
    }

    @PostMapping("/deletePlate")
    public ResultVo deletePlate(@RequestParam("plateId") String plateId) {
        return plateService.deletePlate(plateId);
    }

    @PostMapping("/modifyPlate")
    public ResultVo modifyPlate(@Valid ModifyPlateDTO modifyPlateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("/modifyPlate @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return plateService.modifyPlate(modifyPlateDTO);
    }


    @PostMapping("/addPlate")
    public ResultVo addPlate(@Valid AddPlateDTO addPlateDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("/addPlate @Valid 注解 验证错误");
            System.out.println(bindingResult.getFieldError().getDefaultMessage());
            return ResultVoUtil.error(CodeEnum.ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        return plateService.addPlate(addPlateDTO);

    }


}
